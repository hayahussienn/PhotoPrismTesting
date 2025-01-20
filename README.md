# PhotoPrism Automation Test Repository

## Overview:

This repository contains automated tests for the PhotoPrism web application, focusing on the Photo Search and Filtering functionality. The primary objective is to ensure consistent and efficient testing coverage, integration with CI/CD pipelines, and accurate reporting of test results.


## Objectives

* Automate validation of photo search functionality using keywords.

* Automate verification of filtering options (country, year, month, camera, and category).

* Integrate test execution with CI/CD pipelines for seamless deployment.

* Collect metrics on test execution, coverage, and performance.

## Test Strategies

`Regression Testing`

Re-test the system to ensure updates do not introduce defects.

Full test suite execution for updated application versions.

`Smoke Testing`

Verify critical functionalities such as photo search and filtering.

Execution of a minimal set of high-priority test cases.

`Black-box Testing`

Validate system behavior against requirements without focusing on internal implementation.

Emphasis on input/output validation and user interactions.

## Environment Setup

`Prerequisites`
* Local Environment: Ensure PhotoPrism is installed and running locally.
* Java JDK 11 or higher
* Maven
* Chrome and Firefox browsers (latest versions)


# Installation

### Clone the repository:

` clone https://github.com/yourusername/photoprism-test-automation.git
cd photoprism-test-automation`

### Install dependencies:

` mvn install `

### Configure environment variables:

`export PHOTOPRISM_URL=http://localhost:2342`

`export PHOTOPRISM_USERNAME=admin`

`export PHOTOPRISM_PASSWORD=yourpassword`

### Prepare test data:

Upload a diverse set of photos to your PhotoPrism instance

 Ensure photos include various:

* Keywords (e.g., "Cat", "Dog", "Car")
* Countries
* Timestamps
* Categories
* Color variations



# Running Tests

## Execute All Tests

`mvn test`

## Run Specific Test Suites

## Run only UI tests

`mvn test -Dtest=UITestSuite`

## Run only API tests

`mvn test -Dtest=APITestSuite`

## Run smoke tests

`mvn test -Dtest=SmokeTestSuite`


# CI/CD Pipeline Execution

* Push changes to the repository to trigger the GitHub Actions workflow.

* Test results and reports will be generated automatically and can be viewed in the Actions tab of the repository.



# Test Suite Coverage

Our automated test suite meets the following completion criteria:

### High-Priority Test Cases

#### Photo Search Functionality

* Basic keyword search
* Multi-keyword search
* Search response validation


#### Filtering Operations

* Country-based filtering
* Year/month filtering
* Category filtering
* Color filtering 

#### API Endpoints

* Search API functionality
* Filter API functionality



# Lighthouse Metrics (minimum thresholds)


Accessibility: ≥85
Performance: ≥55
Best Practices: ≥90
SEO: ≥90

# Support

For issues and questions, please:
1. Check existing GitHub issues
2. Create a new issue if needed
3. Contact the test engineering team