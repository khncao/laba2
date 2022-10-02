
# Building Company
Estimate how long it will take and how much it will cost to build different types of buildings. Tool to approximate construction cost based on set of inputs.  

### Notes:
- Currently, calculations and complex queries only vertical integrated with building type material and tool requirements
- Swap JDBC and MyBatis DAO implementations by changing import in BuildEstimateController

### Todo:
- Use more DAOs in services to run more complex calculations using queried data
- Entity/model for employee role average hourly cost per country
- Time and cost breakdown
- Input validation and feedback; ie. address country not supported in dataset
- Cleanup
	- remove unneeded gitignores
	- unimplemented: unsupportedOpException("method descrip not impl")
	- dispose ResultSet
	- avoid unneeded nested try catch blocks with same exceptions
	- move queries to final class scope string
	- async
		- for foreach loops to stream
		- careful with state in different scope
	- xmlParse: take file path as input

### Changelog:
- DAOFactory to get DAOImpl instance for desired entity and impl
- MyBatis impl; refactor to allow swapping to and from JDBC impl
- Switch to DAO pattern for entity database queries; implement simple framework
- Migrate to service interfaces for connection pool, calculating time/cost
- Move project to new repo laba2
- Implement simple entities and queries for BuildingType, Address
- Test database 'testdb.sql' for schema and init test data
- Init GUI views and controllers for login and estimate calculation form
- Init Maven JavaFX project with jdbc mysql connector dependency

- inputs: reasoning
    - address: regional/distance pricing for labor, materials, logistics
    - building type: complexity, scale, material, tools, labor costs
    - sqr meters structure base foundation
    - number of floors
- output: time & cost breakdown, total time & cost
- feature stretch goals: 
  - material/time tiers
  - more precise dimension input
  - constrain/validate inputs(service range address, building type floors/sqrmeter range, etc.)

### App flow
- launch -> show database login prompt
  - login success -> set/show logged in
  - login fail -> try again
- logged in -> query database for form options
- show estimate calculation form -> query building_type or use enum as choices
  - valid input -> update calculated time & cost
  - invalid input -> log error -> try again

## Algorithm
- considerations:
- cost
	- ref: family home, small office, skyscraper, strip mall, apartment, warehouse, stadium
	- each building type should have its own list of approximate scalable material ratios
	- should account for differences between tall buildings such as warehouses versus skyscrapers
	- resource costs will include logistic costs(free delivery) in this project
- time
  - phases include inspection, planning, resource procurement, building
  - building itself can be divided into foundation, framing, structure, furnishing, etc.
  - build time does not account for worker saturation
  - there will possibly be minimal resource bottlenecks other than between planning and laying foundation phases as resources will incrementally arrive on a schedule during prior phases
	
#### Naive cost formula:
```
base_cost + building_type_base_cost
+ foundation_cost_per_sqrmeter * foundation_sqrmeters
+ cost_per_sqrmeter * sqrmeters
```
#### Naive time formula
```
base_time + sum(building_type_base_hours)
+ foundation_hours_per_sqrmeter * foundation_sqrmeters
+ perfloor_hours_per_sqrmeter * sqrmeters
```

- input params:
  - building_type
	- foundation_sqrmeters
	- num_floors
	- sqrmeters: foundation_sqrmeters * num_floors

- query calculated params:
  - building_type: base_cost
  - foundation_cost_per_sqrmeter, cost_per_sqrmeter, foundation_hours_per_sqrmeter, perfloor_hours_per_sqrmeter
    - materials, tools, labor depend on region, available suppliers, building_type, etc.
  - building_type_base_hours
    - in M2M map for building_type and employee_role; building_type_labor_req

- furthest_resource_delivery_time: unimplemented

- base_cost: building company baseline operational costs
- base_time: time to inspect, plan, and other prepapartion; no other progress until complete


## Pseudo
#### Building cost estimate
- From input address, get closest suppliers(material, labor, tool)
  - throw error if missing any supplier in same country
  - Queries: limit company addresses to same zipcode, city, country expanding range as needed; order by distance
  - store results of each supplier company type in respective collections

- Assign fields from inputs and database: 
  - inputs: address, foundation_sqrmeters, num_floors, building_type
  - sqr_meters = foundation_sqrmeters * num_floors
  - building_type: required_materials, required_tools, required_labor
    - Queries: store required resources into respective collections
    - calculate total resources required

- Select suppliers; 
  - do for each required resource entry:
    - iterate companies, check if company meets requirements
      - maybe threshold of total requirement fulfilled to use supplier(ie. only use supplier if they fulfill X% of total required)
      - build collection of viable suppliers
  - OR get resource average in region(zipcode/city/country)

- Accumulate required resources and pricing based on individual suppliers
- Calculate time & cost using populated fields


## Database
"Obvious" fields such as primary_key, name/description, etc. are omitted for brevity.  
"*x_id" field means foreign_key to table x

#### General
    - address
      - line_1
      - line_2
      - line_3
      - zipcode
      - *city_id
    - country
    - city
      - *country_id
    - company
      - *address_id
      - industry(construction, material, labor, tools)
    - employee_role(ie. admin, supervisor, laborer, operator, security, inspector)
    - employee
      - *address_id
      - *company_id
      - *employee_role_id
    - material(ie. M^3 cement, wood board, steel beam)
      - length_meters
      - width_meters
      - height_meters
      - weight_kg
    - tool(ie. digger, crane, dirt_truck)
      - capacity_cubicmeters
      - max_load_kg
      - weight_kg

#### Supplier data
    - material_supplier_inventory
      - *company_id
      - *material_id
      - amount
      - cost_per_amount
    - labor_supplier_laborers
      - *company_id
      - *employee_id
      - hourly_pay_rate
    - tool_supplier_inventory
      - *company_id
      - *tool_id
      - daily_rental_rate
    
#### Building data
    - building_type
      - base_cost
      - min_foundation_sqrmeters
      - max_foundation_sqrmeters
    - building_type_material_req
      - *building_type_id
      - *material_id
      - foundation_amount_per_sqrmeter
      - amount_per_sqrmeter
    - building_type_tool_req
      - *building_type_id
      - *tool_id
      - foundation_hours_of_rental_per_sqrmeter
      - hours_of_rental_per_sqrmeter
    - building_type_labor_req
      - *building_type_id
      - *employee_role_id
      - base_hours_of_work
      - foundation_hours_of_work_per_sqrmeter
      - hours_of_work_per_sqrmeter

### Don't need for build time cost estimate
    - customer
      - *address_id
#### Project data
    - project
      - *address_id
      - *building_type_id
      - foundation_sqrmeters
      - num_floors
      - notes
    - project_employee_link
      - *project_id
      - *employee_id
    - project_customer_link
      - *project_id
      - *customer_id
    - project_material_supplier_link
      - *project_id
      - *company_id
    - project_labor_supplier_link
      - *project_id
      - *company_id
    - project_tool_supplier_link
      - *project_id
      - *company_id
