# HTMLInterfacer

## About
  
HTMLInterfacer is a standalone GUI application which allows static HTML files stored in a GitHub repo to be edited
in a WYSIWYG editor. Similar to a text editor, it allows anyone to update the content, commit the changes
and open a PR, without having to have a working knowledge of Git or IDEs.
  
## Setup
To set up the application, first clone the repo and `cd` into the directory in the terminal. From there run the `setup.sh` script. This will guide you through the setup of the application.
  
Under the hood it creates a `.env` file for the application and runs the `jlink gradle` task. This task creates a JRE containing only the necessary classes to run the application.

When setting up the application you are prompted for the following information (please note this information is required to run the application):
- GitHub account that contains the repo you wish to access the static content from
- The name of the repo you wish to access the static content from
- Oauth token - You will need to ensure you have a PAT set up in your GitHub account which has read/write access to the repo you wish to access. **When you enter the token, nothing will appear in the terminal. This is expected**
- File list - This is a comma separated list of the files you wish to access in the repo. You must provide the full path for each file from the root directory.
- Base branch - This is the base branch within the repo you wish to access. This is usually 'main' or 'master'

Please note:
- All variables are case-sensitive
- Only HTML files can be accessed in the application

Once the setup script is complete, a folder will be created in the parent directory of your current location with the name 'HtmlInterfacer_v1.x'. In a terminal `cd` into this folder and run the `start.sh` script, this will open the application for you.

All logging is undertaken in the `filelog.txt` file created by the application.

### WIP - To do
- Tests -> There are currently issues with using the TestFX package with module-info.java files so there are currently no GUI tests (as of Jan 23). I will revisit this in future. The only way to add tests is by not having a modularised application but I would be unable to use `jlink` and create a smaller JRE for the application. If anyone can provide any further advice on this, please reach out. 

### Improvements

- Recursively get repo contents without manually adding file list to environment vars

Feel free to open an issue for any bugs/improvements

#### License
GNU GPLv3
