# MyEduPlanner

An Android application designed for trainers and educators to create, manage, and track **Learning Plans** and **Session Plans** based on KTTC (Kenya Technical Trainers College) templates.

Youtube Links:
- **MyEduPlanner – Development Environment & Source Code Verification** 
https://www.youtubeeducation.com/watch?v=1uMNCLA49yA  

- **MyEduPlanner – Working Prototype Demonstration**  
https://www.youtubeeducation.com/watch?v=Vx-mPDOpPAw

  
## 📱 Features

### Core Functionality
- **Learning Plan Generator** - Create comprehensive learning plans with unit details, weekly sessions, and assessment criteria
- **Session Plan Generator** - Build detailed session plans with step-by-step delivery instructions
- **History & Management** - View, edit, and delete previously created plans
- **Settings** - Set default values to auto-fill forms and save time
- **Search** - Quickly find plans by title or unit code
- **Document Generation** - Export plans as HTML files for viewing and printing

### Key Highlights
- 📋 Based on official KTTC templates (REF: KTTC/TP/LP/F06 & F07)
- 💾 Local database storage using Room
- 🔍 Search and filter functionality
- ✏️ Edit and update existing plans
- 📂 Organized file management in Downloads/MyEduPlanner
- 🎨 Clean, intuitive Material Design interface

---

## 🛠️ Technologies Used

- **Language**: Kotlin
- **Architecture**: MVVM with Repository pattern
- **Database**: Room (SQLite)
- **UI**: XML layouts with ViewBinding
- **Async**: Kotlin Coroutines + Flow
- **File Management**: FileProvider for secure file sharing
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)

---

## 📂 Project Structure

```
MyEduPlanner/
├── app/src/main/
│   ├── java/com/example/myeduplanner/
│   │   ├── MainActivity.kt                 # Home screen
│   │   ├── LearningPlanActivity.kt         # Learning plan form
│   │   ├── SessionPlanActivity.kt          # Session plan form
│   │   ├── HistoryActivity.kt              # View saved plans
│   │   ├── SettingsActivity.kt             # Default settings
│   │   ├── LearningPlan.kt                 # Data class
│   │   ├── SessionPlan.kt                  # Data class
│   │   ├── AppSettings.kt                  # SharedPreferences manager
│   │   ├── PdfGenerator.kt                 # HTML file generator
│   │   ├── DatePickerHelper.kt             # Date selection utility
│   │   ├── LearningPlanAdapter.kt          # RecyclerView adapter
│   │   ├── SessionPlanAdapter.kt           # RecyclerView adapter
│   │   └── database/
│   │       ├── AppDatabase.kt              # Room database
│   │       ├── LearningPlanEntity.kt       # Database entity
│   │       ├── SessionPlanEntity.kt        # Database entity
│   │       ├── LearningPlanDao.kt          # Data access object
│   │       ├── SessionPlanDao.kt           # Data access object
│   │       └── PlanRepository.kt           # Repository pattern
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_main.xml
│   │   │   ├── activity_learning_plan.xml
│   │   │   ├── activity_session_plan.xml
│   │   │   ├── activity_history.xml
│   │   │   ├── activity_settings.xml
│   │   │   └── item_plan.xml              # RecyclerView item
│   │   └── xml/
│   │       └── file_paths.xml             # FileProvider config
│   ├── assets/
│   │   ├── learning_plan_template.html
│   │   └── session_plan_template.html
│   └── AndroidManifest.xml
├── build.gradle.kts (app)
└── build.gradle.kts (project)
```

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- JDK 11 or higher
- Android SDK with API Level 24+

### Installation

1. **Clone or download the project**
   ```bash
   git clone https://github.com/yourusername/MyEduPlanner.git
   cd MyEduPlanner
   ```

2. **Open in Android Studio**
    - Open Android Studio
    - Select "Open an Existing Project"
    - Navigate to the project folder and click OK

3. **Sync Gradle**
    - Android Studio will automatically sync Gradle
    - Wait for dependencies to download

4. **Run the app**
    - Connect an Android device or start an emulator
    - Click the "Run" button (green triangle)

---

## 📖 How to Use

### 1. First Time Setup
1. Open the app
2. Tap **Settings** (⚙️)
3. Fill in your default information:
    - Trainer Name
    - Admission Number
    - Institution
    - Level
    - Class
    - Unit Code
    - Unit of Competence
    - Number of Trainees
4. Tap **Save Settings**

### 2. Create a Learning Plan
1. From the home screen, tap **Learning Plan**
2. Form fields will auto-fill with your default settings
3. Fill in the required fields (marked with *)
4. Tap **Generate Learning Plan**
5. The plan is saved to the database and exported as HTML

### 3. Create a Session Plan
1. From the home screen, tap **Session Plan**
2. Form fields will auto-fill with your default settings
3. Fill in all session details including steps
4. Tap **Generate Session Plan**
5. The plan is saved and exported as HTML

### 4. View & Manage Plans
1. Tap **History** (📋) from the home screen
2. Switch between Learning Plans and Session Plans tabs
3. Use the search bar to find specific plans
4. For each plan, you can:
    - **View** - Open the HTML document
    - **Edit** - Modify and update the plan
    - **Delete** - Remove the plan permanently

### 5. Generated Files
- All generated HTML files are saved to:
  ```
  /storage/emulated/0/Download/MyEduPlanner/
  ```
- Files can be opened with any web browser
- Files can be printed or converted to PDF using a browser

---

## 🗄️ Database Schema

### LearningPlanEntity
```kotlin
- id: Long (Primary Key)
- unitOfCompetence: String
- unitCode: String
- trainerName: String
- institution: String
- level: String
- week: String
- sessionNo: String
- sessionTitle: String
- learningOutcome: String
- trainerActivities: String
- traineeActivities: String
- ... (24 fields total)
- createdAt: Long
- updatedAt: Long
- pdfFilePath: String
```

### SessionPlanEntity
```kotlin
- id: Long (Primary Key)
- date: String
- time: String
- trainerName: String
- institution: String
- unitCode: String
- sessionTitle: String
- learningOutcomes: String
- step1Time: String
- step1Trainer: String
- step1Trainee: String
- ... (44 fields total)
- createdAt: Long
- updatedAt: Long
- pdfFilePath: String
```

---

## 🔑 Key Dependencies

```gradle
// Room Database
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
kapt("androidx.room:room-compiler:2.6.1")

// Core Android
implementation("androidx.core:core-ktx:1.x.x")
implementation("androidx.appcompat:appcompat:1.x.x")
implementation("com.google.android.material:material:1.x.x")
implementation("androidx.constraintlayout:constraintlayout:2.x.x")

// Lifecycle & Coroutines
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.x.x")
```

---

## 📝 Permissions

The app requires the following permissions:

```xml
<!-- For saving files (Android 9 and below) -->
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
    android:maxSdkVersion="28" />

<!-- For reading files (Android 12 and below) -->
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
    android:maxSdkVersion="32" />
```

**Note**: Android 10+ uses scoped storage, so these permissions are only needed for older devices.

---

## 🎨 UI Components

### Main Screen
- Settings button (orange)
- History button (purple)
- Learning Plan button (green)
- Session Plan button (blue)

### Forms
- Auto-fill functionality from settings
- Date picker for date fields
- Multi-line text areas for detailed content
- Validation for required fields
- Save and update functionality

### History Screen
- Tab switcher (Learning Plans / Session Plans)
- Search bar with real-time filtering
- RecyclerView with plan cards
- Action buttons: View, Edit, Delete
- Empty state messages

---

## 🐛 Troubleshooting

### App crashes on launch
- Check that all dependencies are properly synced
- Verify minSdk is set to 24 or higher
- Clear cache: Build > Clean Project > Rebuild Project

### Can't view generated files
- Check file permissions
- Verify FileProvider is configured in AndroidManifest.xml
- Check that file_paths.xml exists in res/xml/

### Database errors
- Uninstall and reinstall the app to reset the database
- Check Room version compatibility
- Verify entity annotations are correct

### Form doesn't save
- Check internet connection is not required (app works offline)
- Verify all required fields are filled
- Check storage permissions

---

## 🔮 Future Enhancements

- [ ] PDF export (currently HTML only)
- [ ] Cloud backup and sync
- [ ] Multiple trainers/users support
- [ ] Template customization
- [ ] Export to Word/Excel formats
- [ ] Offline backup/restore
- [ ] Dark mode support
- [ ] Plan duplication feature
- [ ] Analytics and reporting

---

## 👨‍💻 Developer Notes

### Building for Release
1. Update version in `build.gradle.kts`:
   ```kotlin
   versionCode = 2
   versionName = "1.1"
   ```

2. Generate signed APK:
    - Build > Generate Signed Bundle / APK
    - Select APK
    - Create or select keystore
    - Fill in key details
    - Select "release" build variant

### Code Style
- Follow Kotlin coding conventions
- Use meaningful variable names
- Add comments for complex logic
- Keep functions small and focused

---

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📧 Contact

For questions, issues, or suggestions, please open an issue on GitHub.

---

## 🙏 Acknowledgments

- Kenya Technical Trainers College (KTTC) for the template formats
- Android Room Database documentation
- Material Design guidelines

---

**Made with ❤️ for educators and trainers**
