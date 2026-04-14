# Text Analyzer Service 🚀

A robust Spring Boot application built with **Java 21** designed to perform deep text analysis. It calculates metrics
such as word frequency, average length, and identifies the longest words, persisting all results in an H2 in-memory
database.

## 🛠 Tech Stack

* **Language:** Java 21
* **Framework:** Spring Boot 3.x (Web, Data JPA, Validation)
* **Database:** H2 (In-memory)
* **Mapping:** Manual Mapping (Component-based Mapper)
* **Tools:** Lombok, Maven, Mockito, AssertJ
* **Architecture:** Layered Architecture (Controller -> Service -> Repository)

## ✨ Key Features

* **Text Analysis:** Automated calculation of:
    * Total character length.
    * Total word count.
    * Longest word (with alphabetical fallback).
    * Average word length.
    * Top-N most frequent words (configurable).
* **Persistence:** All analyzed results are stored in an H2 database using Spring Data JPA.
* **Validation:** Robust input validation using Hibernate Validator to ensure no blank submissions.
* **Global Exception Handling:** Standardized error responses for missing resources (`404`), validation errors (`400`),
  and internal issues (`500`).

## 📋 API Reference

### Text Statistics Endpoints

| Method | Endpoint               | Description                   |
|:-------|:-----------------------|:------------------------------|
| `POST` | `/api/text-stats`      | Analyze and save new text     |
| `GET`  | `/api/text-stats`      | Retrieve all analysis history |
| `GET`  | `/api/text-stats/{id}` | Get specific analysis by ID   |

#### Sample Request (POST /api/text-stats)

```json
{
  "text": "Hello World. Hello Java!"
}
```

#### Sample Response

```json
{
  "id": 1,
  "originalText": "Hello World. Hello Java!",
  "length": 24,
  "wordCount": 4,
  "longestWord": "Hello",
  "averageWordLength": 4.75,
  "mostFrequentWords": {
    "Hello": 2,
    "Java": 1,
    "World": 1
  }
}
```

## ⚠️ Error Handling

The application uses a @RestControllerAdvice to ensure consistent error formats across the API.

| Status             | Scenario                                                    |
|:-------------------|:------------------------------------------------------------|
| 400 Bad Request    | Validation failure (e.g., empty text) or illegal arguments. |
| 404 Not Found      | Requesting a TextStats ID that does not exist.              |
| 500 Internal Error | Unexpected server-side issues.                              |

**Example Error Body:**

```json
{
  "message": "Text statistics with id 999 does not exist.",
  "statusCode": 404,
  "timestamp": "2026-04-14T16:30:48Z"
}
```

## ⚙️ Configuration

The application is highly configurable via src/main/resources/application.yml:

```yaml
text-analyzer:
  word-frequency: 3 # Number of top frequent words to return in the analysis
```

## 🧪 Testing

The business logic is covered by comprehensive unit tests using **Mockito** and **AssertJ**.

* **Positive Scenarios:** Verified successful text analysis, mapper transformations, and persistence.
* **Negative Scenarios:** Verified correct exception throwing and handling when IDs are missing or inputs are invalid.

Run tests using Maven:

```bash
mvn test
```

## 🚀 Getting Started

1. **Clone the repository:**
   git clone https://github.com/rudyevhenii/text-analyzer-rud.git

2. **Build the project:**
   mvn clean install

3. **Run the application:**
   mvn spring-boot:run

4. **Access H2 Console:**
   Navigate to http://localhost:8080/h2-console
    * JDBC URL: jdbc:h2:mem:text_analyzer
    * User: sa
    * Password: (empty)
