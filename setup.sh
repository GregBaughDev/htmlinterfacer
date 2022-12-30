echo "HTML Interfacer - Setup"
echo "This script will ask you a number of questions to setup the program for you."
echo "It will create a file containing environment vars and compile the application \ninto an executable file."
echo "*****************************************************************************"
echo "Please enter the name of the GitHub account you will access the repo from:"
read GHOWNER
echo "*****************************************************************************"
echo "Please enter the name of the GitHub repo you will access the HTML files from:"
read GHREPO
echo "*****************************************************************************"
echo "Please enter the PAT (Personal Access Token) from your GitHub user account."
echo "N.B Your token must have read/write access for the repo."
read -s OAUTH
echo "*****************************************************************************"
echo "Please enter the list of files from the repo you would like to access."
echo "These must contain the full path to the file and be separated by commas"
echo "and the string must contain all file names."
echo "i.e test/folder/file.html,test2/folder/file.html"
read FILES
echo "*****************************************************************************"
echo "Please enter the name of the base branch"
echo "i.e main or master (ensure the case matches GitHub)"
read BASE_BRANCH

touch .env
echo "GHOWNER=${GHOWNER}" >> .env
echo "GHREPO=${GHREPO}" >> .env
echo "OAUTH=${OAUTH}" >> .env
echo "FILES=${FILES}" >> .env
echo "BASE_BRANCH=${BASE_BRANCH}" >> .env
echo "*****************************************************************************"
