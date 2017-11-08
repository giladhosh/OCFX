# OpenClinica Searcher
## Requirements
In order to use this tool you will need to have an online OpenClinica server.
This version of OpenCLinica Searcher works with OpenClinica Version: 3.5 - Changeset: 76b33d904238 2015-04-23 17:55 +0000.
Updating the Openclinica server could mean this tool code should be reviewed and modified accordingly (although it is possible it should need no changes to it).
## Intro
Openclinica is a clinical data management tool used for electronic data capture
and clinical data management. For more info go to [OpenClinica] website.
### Requirements
* An online OpenClinica server.
* You must have privelleges for the specific study you are attempting to search in. A valid user name and password is a must.

### Work flow
Running a search is very simple: use the navigation frame on the right side of the screen to open the "free search" option, type in any phrase you'd like to search for and click search.

### Refresh Data
The data is extracted from the online server at loading time. If you have recently updated the data on the server or you are finding the data unreliable for any reason you can manually retreive all data from the online server, this is done via the "Refresh Data" button on the left pane.

### Tech
* Java 8
* JavaFX GUI using FXML
* JSON

#### License
This tool uses classes from the RadPlanBio.

###### Questions & Remarks can be sent to giladhos@post.bgu.ac.il
[//]: # (These are reference links used in the body of this note)
[OpenClinica]: <https://www.openclinica.com/>
