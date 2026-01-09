# Duck Duck Social - Android App

Social network Android application built with Kotlin and Jetpack Compose.

## Prerequisites

### Required Backend API
This app requires the Laravel API backend to function.

**API Repository**: https://github.com/CPNV-ES/NOS1-TAB

Start the API:
```bash
cd DuckDuckAPI
php artisan serve
```

API must be accessible at `http://10.0.2.2:8000` (Android emulator) or your local IP for physical devices.

### Design
**Figma**: https://www.figma.com/design/0MDHG1lZ40UIJkYKcJOlj0/main-page?node-id=3-1514&t=tBEH1IV0HXV71Sud-0

### Requirements
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17+
- Kotlin 1.9.0+
- Min SDK: 24 (Android 7.0)
- Target SDK: 34 (Android 14)

## Quick Start

1. **Clone the repository**
```bash
git clone [REPO_URL]
cd GUI2-TAB
```

2. **Open in Android Studio**
- File → Open → Select project folder
- Wait for Gradle sync

3. **Start the API** (see link above)

4. **Run the app**
- Connect device or start emulator
- Click Run (▶️)


## Architecture

```
app/src/main/java/ch/cnpv/gui2/
├── data/                   # Data sources
├── models/                 # Data models (Post, Profil, Comment)
├── network/                # Retrofit API configuration
├── ui/
│   ├── components/         # Reusable UI components
│   ├── screen/            # App screens
│   ├── viewmodel/         # Business logic
│   └── AppNavHost.kt      # Navigation
└── MainActivity.kt
```

**Pattern**: MVVM (Model-View-ViewModel)


## Configuration

### Network URLs
- **Emulator**: `http://10.0.2.2:8000`
- **Physical device**: `http://[YOUR_LOCAL_IP]:8000`

Edit `BASE_URL` in `RetrofitInstance.kt` if needed.


