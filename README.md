# Intro

The goal of this homework is to install the core tooling that is used throughout the semester and to learn its basic functionality: build (Maven), revision control (Git Feature Branch Workflow), code documentation (JavaDoc), lint-ing (CheckStyle), and logging (SLF4J). Many of the steps require research to complete. This lack of specificity is intentional, and students are encouraged to use the Internet to fill in the missing gaps.

At the end of this homework, a student should be able to:

   * Manage code development with Git and the feature branch workflow  
   * Configure and use Maven to manage the build lifecycle of a project
   * Write JavaDocs and run JavaDoc via Maven to generate documentation
   * Run CheckStyle via Maven to lint source code 
   * Add via Maven and then use SLF4J logging with the logback binding to the console

# Pre-requisites

This homework requires Git and Maven be available in a command line interface (CLI): `git` and `mvn`. Git is commonly available by default in Linux and Mac OSX. Windows provides several options. Maven is most easily installed via package manager (e.g., [Homebrew Cask](https://brew.sh/) for Mac OSX). There is no single preferred shell for the CLI as long as `git` and `mvn` are runnable from the prompt.

The course does not rely on any single platform; the tools are available to Windows, OS X, and Linux. Windows users might consider using [Windows Subsystem for Linux](https://en.wikipedia.org/wiki/Windows_Subsystem_for_Linux). OS X and Linux are fairly straightforward with available package managers.

This course is Java based and requires a Java 1.8 JDK. As with the other tools, the JDK is most easily installed via package manager. The `javac` and `java` tools should be available from the CLI for `mvn`. 

The course is IDE agnostic so that students are free to use any preferred IDE or editor. Maven will manage the build lifecycle for the homework and projects in the CLI as mentioned previously. Students are welcome to use the GUI features in a preferred IDE, but all the grading and support is via CLI with `git` and `mvn`. Students are expected to be proficient with these tools from the CLI. General IDE issues are considered outside the scope of TA and instructor support.

# Homework 

## Part I: Feature Branch Workflow in Git

Study the [feature branch workflow](https://www.atlassian.com/git/tutorials/comparing-workflows/feature-branch-workflow). The repository created by the invitation request from GitHub Classroom is the central repository. The master branch on the central repository is the latest state of the project.

Complete this part of the homework by creating a feature branch for the homework in your local repository. At the end of this homework, the local feature branch will be pushed to the central repository and then used to create a pull request. Give the branch a descriptive name.

Be mindful of the commits on the feature branch. Each commit should be self-contained and reflect a set of changes that logically belong together. Students are expected to adhere to [commit message guidelines](https://gist.github.com/robertpainsi/b632364184e70900af4ab688decf6f53).

## Part II: Build Lifecycle Management with Maven

Track down some Java code that you wrote from a previous class, or write some Java code for this homework. The amount or size of the code should be no more than a few hundred lines of code and not tens or thousands of lines of code (i.e., the code should be small and not tiny or medium). The code should be in at least two or more different classes.

Complete this part of the code by creating a `pom.xml` file to manage the build life-cycle of the code and run the code. The `pom.xml` file only needs to be able to compile the code and package it in a jar-file for now. There are many ways to create the `pom.xml` file. For example, follow the [Your First Maven Project](http://tutorials.jenkov.com/maven/your-first-maven-project.html) tutorial for the code or using one of several different [Maven Archetypes](https://maven.apache.org/guides/introduction/introduction-to-archetypes.html) for Java such as [maven-archetype-simple](https://maven.apache.org/archetypes/maven-archetype-simple/) or [maven-archetype-quickstart](https://maven.apache.org/archetypes/maven-archetype-quickstart/). A little looking through the many choices with `mvn archetype:generate -Dfilter=java` gives many options including a few from BYU and a very nice [java8-minimal-quickstart](https://github.com/spilth/java8-minimal-quickstart).

If using the any of the Maven archetypes such as simple or quickstart, then be aware that it puts most the plugins in the [Plugin Management](https://maven.apache.org/pom.html#Plugin_Management) section of the `pom.xml` file. Stack Overflow has a nice discussion about [plugin management in Maven](https://stackoverflow.com/questions/10483180/what-is-pluginmanagement-in-mavens-pom-xml) the short of which is that it configures plugins for builds that inherit the `pom.xml`. To work for this build, they need to appear outside the Plugin Management as well.

Maven is an intimidating tool and does take some time to learn (which is some of the goal of this homework). Be patient and use Google. This part of the homework is complete when the `pom.xml` file builds the Java code and packages it in a jar that can be used to run the code. Please use a sensible group and artifact ID for the course such as `edu.byu.cs329` for the group ID and `hw0` for the artifact ID.

Running the code with Maven is fairly straightforward. Define a class with a `main` method and then use [mvn exec:java](http://www.vineetmanohar.com/2009/11/3-ways-to-run-java-main-from-maven/). 

## Part III: Code Documentation with JavaDoc

It is expected that all code is documented via [JavaDoc](https://www.oracle.com/technetwork/java/javase/documentation/index-jsp-135444.html) following the Java Software Oracle [style guide](https://www.oracle.com/technetwork/java/javase/documentation/index-137868.html). There is a good example at the end of the [style guide](https://www.oracle.com/technetwork/java/javase/documentation/index-137868.html) that represents what is expected for the course.

All JavaDocs are generated using the [Apache Maven JavaDoc Plugin](https://maven.apache.org/plugins/maven-javadoc-plugin/usage.html). Complete this part of the homework by configuring Maven to generate JavaDocs in reporting as a part of the `mvn site` build life cycle, write JavaDocs for one of the classes in your code, and be sure those docs are correctly generated with no warnings or errors.

## Part IV: Lint-ing with CheckStyle

Static code analysis is known to reduce code defects. This course uses [CheckStyle](https://checkstyle.sourceforge.io/) with the [Google rule set](https://checkstyle.sourceforge.io/google_style.html) via Maven for a linter. The build phase should warn if any code does not pass CheckStyle.

Complete this part of the homework by configuring the [Maven CheckStyle Plugin](https://maven.apache.org/plugins/maven-checkstyle-plugin/) for the [Google rule set](https://checkstyle.sourceforge.io/google_style.html). Configure the plugin to be a part of the reporting life cycle and to fail the build in the validate phase when CheckStyle does not pass. In this configuration CheckStyle will always run: see [usage](https://maven.apache.org/plugins/maven-checkstyle-plugin/usage.html) for Maven configuration examples. Modify your code to pass CheckStyle on the Google rule set.

There are two ways to add in the [Google rule set](https://checkstyle.sourceforge.io/google_style.html) in the `pom.xml` file. Both involve setting the `configlocation` tag in the `configuration` section to specific values. The first, and easiest, way is to simply specify **google_checks.xml** for the value. This file ships with the latest version of CheckStyle and is known. The second way has you download the [google_checks.xml](https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/google_checks.xml) file and put it in the root directory with the `pom.xml` file. Both are fine solutions.

## Part V: Logging with SLF4J

Logging is an important part of debugging and fault isolation for removing code defects. This course is going to use [SLF4J](https://www.slf4j.org/) (Simple Logging Facade for Java) and [Logback](https://logback.qos.ch/) for the binding library SLF4J. 

The way logging is intended to be used in this course is as a mechanism to support debugging. Most often console output is the first line of attack to isolate and fix a program defect (let's be honest). The actual debugger is is the second line of attack. Both are invaluable tools. The logger is intended to help support console output for debugging, but rather than have to remove all that output once the fault is isolated, it can all be left in place and simply turned off by changing the log-level.

To complete this part of the homework, edit the Maven `pom.xml` file to add support for [SLF4J](https://www.slf4j.org/) with the [Logback](https://logback.qos.ch/) or [Log4j 2](https://logging.apache.org/log4j/2.x/) binding library. Create an appropriate configuration file that connects the logger to STDOUT. A careful [Google search](http://bfy.tw/LdDI) will find several helpful resources. Add logging to the JavaDoc documented class at the *debug*, *info*, *warn*, *trace*, and *error* levels. Modify the configuration to experiment with the different log-levels available and explore how the console output is changed by the configure log level.

## Part VI: .gitignore

Version control is most effective when it only tracks and reports files that are pertinent to the build and deployment of the project. It can be confusing when version control constantly reports information on non-essential files. It is expected that students include an appropriate `.gitignore` file in all project repositories to only track important files and artifacts.

Complete this part of the homework by creating and adding a `.gitignore` file to the project (if it has yet to be added) that ignores build artifacts, IDE project files, and any other files not essential to the Maven life-cycle management.

## Part VII: Create a Pull Request

Complete this part of the homework by pushing your local feature branch to the central repository and then creating a [pull request](https://help.github.com/en/articles/creating-a-pull-request) for the feature. Pull requests are expected to roughly follow this [style guide](https://www.braintreepayments.com/blog/effective-pull-requests-a-guide/). Be sure to directly reference the @-reference the instructor for notification. 

# Grading Rubric

| Problem | Point Value | Your Score |
| ------- | ----------- | ---------- |
| Descriptive branch name | 5 | |
| Pull request adheres to style guide | 10 | |
| Pull request @references the instructor for notification | 5 | |
| Commits organized in logical units | 10 | |
| Commit messages adhere to standard guidelines | 10 | |
| `.gitignore` file to ignore non-essential files | 5 | |
| Java 1.8 Set in `pom.xml`| 5 | |
| Logging support with a binding is in the `pom.xml`    file | 5 | |
| `mvn` configured to compile code | 5 | |
| Code builds with no warnings or errors | 10 | |
| Build runs CheckStyle and reports warnings and errors | 5 | |
| Code passes CheckStyle on the Google rule set with no warnings or errors | 20 | |
| `mvn site` generates JavaDocs with no warnings or errors | 5 | |
| JavaDocs are given for one class that adheres to the style guide | 25 | |
| The JavaDoc documented class includes logging at all levels | 10 | |
| Total | 135 | |
