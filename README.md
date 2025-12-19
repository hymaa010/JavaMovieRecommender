# 🎬 Java Movie Recommender

A Java console application that recommends movies based on genres the user already likes. It reads user and movie data from text files, validates them, generates smart recommendations, and saves the results automatically.

---

## 🚀 Features

* **Genre-Based Recommendations**
  Suggests new movies that match the genres of movies the user previously enjoyed.

* **Strict Data Validation**
  Ensures:

  * User IDs are exactly **9 alphanumeric characters**
  * Movie IDs follow the **letter(s) + number(s)** pattern

* **File Parsing**
  Loads data from structured text files (`users.txt`, `movies.txt`) using dedicated parser classes.

* **Automatic Output**
  Generates a clean `recommendations.txt` file with personalized movie suggestions.

---

## 📂 Project Structure

```
src/
├── main
│   └── java
│       └── com/example
│           ├── Entities
│           │   ├── Movie.java
│           │   └── User.java
│           │
│           ├── Readers
│           │   ├── movieInput.java
│           │   └── userInput.java
│           │
│           ├── Validators
│           │   ├── movie_validator.java
│           │   └── user_validator.java
│           │
│           ├── Writers
│           │   └── Recommendation.java
│           │
│           ├── Resources
│           │   ├── movies.txt
│           │   └── users.txt
│           │
│           └── Main.java
│
└── test
    └── java
        ├── MovieTest.java
        ├── UserTest.java
        ├── UserValidatorTest.java
        ├── movieValidatorTest.java
        ├── ReaderEntityIntegrationTest.java
        ├── RecommendationEndToEndTest.java
        │
        └── TopToDownTesting
            ├── RecommendationTest_Level1.java
            ├── MovieInputTest_Level2.java
            └── UserInputTest_Level2.java
```

---

## 🛠️ Setup & Usage

### 1️⃣ Clone or Download the Repository

Get the project locally—via Git or ZIP.

### 2️⃣ Set File Paths

Open **Main.java** and update the paths for:

* `movies.txt`
* `users.txt`

Make sure they match your local directories.

### 3️⃣ Compile the Project

```bash
javac com/example/*.java
```

### 4️⃣ Run the Application

```bash
java com.example.Main
```

### 5️⃣ View the Output

Open the auto-generated:

```
recommendations.txt
```

It will contain all users and their recommended movies.

---

## 📝 Input File Formats

### **movies.txt**

Two lines per movie:

```
Movie Title, MovieID
Genre1, Genre2, Genre3
```

### **users.txt**

Two lines per user:

```
User Name, UserID
LikedMovieID1, LikedMovieID2, ...
```

## 🧪 Testing

The `test/` directory contains unit tests that check the correctness of your data models and input parsers.
These tests ensure:

* movies are read and validated properly
* users are parsed with correct ID checks
* genre lists and relationships are processed correctly
  
---




=
