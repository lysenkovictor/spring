Warehouse storage service
General requirements
Create service to handle current warehouse storage status.
Required functionality
* add new product owner
* remove product owner (only product owners without any item currently
stored at warehouse could be removed
* prove list of active product owners
* store new item (items could be stored only for already known product
owners)
* withdraw item from warehouse (it is common situation with withdrawing
several different items for one product owner at once)
* provide list of all items for given product owner
* provide statistic information about amount and total cost of items stored
and withdrawn for the last day/month
* provide statistic information about active product owners: list of top 5
product owners according to the total cost of items currently stored at warehouse, sorted by total price, starting with the biggest one
Required data information
Item:
* title
* category (any item could have several categories) • count
* price (for one unit)
* storing date
Product Owner:
* first name
* last name
* company name (if any)
* contact (could be private email, work email, private phone number, work
phone number; client can have several of them, but at least one)
