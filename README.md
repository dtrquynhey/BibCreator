# BibCreator
Winter 2023 LaSalle College - Object-Oriented Programming II - Final Project

BibCreator is a Java application designed for processing BibTeX files, which are commonly used for organizing bibliographic references. This tool can take BibTeX files as input and create corresponding JSON files in different formats (IEEE, ACM, NJ) for further analysis and use. It's particularly useful for managing academic references.


## Table of Contents
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
  - [Running the Application](#running-the-application)
  - [Processing BibTeX Files](#processing-bibtex-files)
- [Project Structure](#project-structure)
- [License](#license)
- [Acknowledgements](#acknowledgements)


  
## Getting Started
<a name="getting-started"></a>

### Prerequisites
<a name="prerequisites"></a>
- Java Development Kit (JDK) 8 or later
- A Java Integrated Development Environment (IDE) such as IntelliJ IDEA (optional but recommended)

### Installation
<a name="installation"></a>
1. Clone this repository to your local machine.

   ```
   git clone <repository-url>
   ```

2. Ensure you have a Java Development Kit (JDK) installed on your system. You can download it from [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html) or use an open-source JDK distribution like [OpenJDK](https://openjdk.java.net/).


## Usage
<a name="usage"></a>

### Running the Application
<a name="running-the-application"></a>
1. Open your command prompt or terminal.

2. Navigate to the project directory where you've cloned this repository.

   ```
   cd BibCreator
   ```

3. Compile the Java source files using the following command:

   ```
   javac -cp src src/test_package/BibCreator.java
   ```

4. Run the application:

   ```
   java -cp src test_package.BibCreator
   ```

The application should start, and you'll be prompted for further actions.

### Processing BibTeX Files
<a name="processing-bibtex-files"></a>
The application expects BibTeX files to be located in the project directory. It will process these files and generate JSON files (IEEE, ACM, NJ) based on the provided references.


## Project Structure
<a name="project-structure"></a>
The project directory is structured as follows:

- `src/`: Contains the source code organized into packages.
  - `basic_classes/`: Classes related to article processing and formatting.
  - `exception_classes/`: Custom exception classes for error handling.
  - `test_package/`: The main package containing the BibCreator application.
- `Latex1.bib`, `Latex2.bib`, etc.: Example BibTeX files (you can add more).
- `ACM1`, `ACM2`, etc.: Output ACM JSON files.
- `IEEE1`, `IEEE2`, etc.: Output IEEE JSON files.
- `NJ1`, `NJ2`, etc.: Output NJ JSON files.


## License
<a name="license"></a>
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.


## Acknowledgments
<a name="acknowledgements"></a>
Special thanks to @SizarStass for their valuable contributions to this project.
