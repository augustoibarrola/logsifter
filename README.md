Goal is to have project that

takes in a file and a word or words

and filters through the file looking for that word

the file should be some sort of log

has to use a few types of collections

has to describe their Big O notation.

The input of the ja:va file may be large, so we have to accomadate for this. The methods
provided by the File class are not suffecient in cases where large log files need to be
sifted through.

- Am using [mingrammer/flog](https://github.com/mingrammer/flog) to make fake log outputs for example parses. 

## TO-DO

- [ ] Add hot reload for tomcat  
_Everytime I change code, I need to go into the terminal, stop the servlet if it is running, and thenr rerun `mvn spring-boot:run` for the changes to take effect. <mark>I want to be able to save the changed source code and the application immediately refreshes.</mark>_