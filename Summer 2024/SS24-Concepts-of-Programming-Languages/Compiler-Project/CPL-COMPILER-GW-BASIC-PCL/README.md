# Summer Semester 2024 - Hochschule für Technik Stuttgart
## Concepts of Programming Languages - GW Basic to PCL Compiler Project

### Acknowledgement
We would like to thank <b>Professor Peter Heusch</b> for helping and training us on the compiler modules.

### Team Members
1. Abhishek Kumar 
2. Hardik Manubhai
3. Sanjay Prabhu Kunjibettu
4. Delcy D'Souza

### Project Specifications

| Category            | Tech Stack                |
|---------------------|---------------------------|
| Compiler Tech Stack | JavaCC and JJTree with ANT|
| Programming Language | JAVA |
| LIDA Server | https://lsf.hft-stuttgart.de/qisserver/ |

### How to run the project?

1. Unzip the project file in to a folder on the LIDA Server
2. Open the terminal inside the folder
3. Run the following commands:

#### 3.1 Command to clean, initialize and compile the ant build required for the project
> ant all

#### 3.2 Command to run the input.bas file
> ant run -Dfile "gw-basic-files/MoodleExample.bas"