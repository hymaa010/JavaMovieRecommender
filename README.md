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
src/com/example/
│
├── Main.java              // Program entry point
│
├── User.java              // User data model
├── Movie.java             // Movie data model
│
├── userInput.java         // Reads & validates user file
├── movieInput.java        // Reads & validates movie file
│
└── Recommendation.java    // Recommendation engine
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
