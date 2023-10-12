# Branch Manager Address Book API

Acceptance Criteria
To develop an address book application to keep track of the customer records.

- AC 1: Address book will hold name and phone numbers of contact entries
- AC 2: Users should be able to add new contact entries
- AC 3: Users should be able to remove existing contact entries
- AC 4: Users should be able to print all contacts in an address book
- AC 5: Users should be able to maintain multiple address books
- AC 6: Users should be able to print a unique set of all contacts across multiple address books


## REST ENDPOINTS

#### ADD Address book entry
*POST* 

`` http://localhost:9000/api/v1/reece-address-book/contacts/{address book name} ``

#### Payload

```
{
	"name": "abc",
    "number": "0450 000 000"
}
```

#### GET Address book entries - all address book entries
*GET*

``http://localhost:9000/api/v1/reece-address-book/contacts``

#### GET Address book entries - unique entries only
*GET*

`` http://localhost:9000/api/v1/reece-address-book/contacts?unique=true ``

#### DELETE Address book entry
*DELETE*

`` http://localhost:9000/api/v1/reece-address-book/delete_contacts/{contact number} ``


#### Swagger documentation

`` http://localhost:9000/swagger-ui.html#/controller ``

#### Docker Image:

The docker image can be pulled from snaveendas/sandbox:latest and run using the following commands:

- ``docker pull snaveendas/sandbox:latest``

- ``docker run -p 8000:9000 -t snaveendas/sandbox``
