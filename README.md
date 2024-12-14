# Weather API App

This is a weather application that provides current weather data for a specified city. The app fetches weather information and displays it in a user-friendly interface.

## Features
- Displays current weather conditions (temperature, humidity, feels-like temperature, UV index).
- Fetches data from a weather API.
- Includes a mock repository for testing in debug mode.

## Setup Instructions

### 1. Clone the Repository
Clone this repository to your local machine using:

```bash
git clone https://github.com/yourusername/weatherapiapp.git
```

### 2. Open the Project
Open the project in Android Studio.

### 3. Setup gradle.properties File
To enable API key integration, you'll need to add your personal Weather API key to the `gradle.properties` file.

- Open the `gradle.properties` file located in the root of your project.
- Add your personal Weather API key like so:

```properties
weather_api_key=your_personal_api_key_here
```
Note: You can sign up for a free API key at WeatherAPI if you don’t have one.

### 4. Sync the Project
After adding the API key to `gradle.properties`, sync the project with Gradle. In Android Studio, click `File > Sync Project with Gradle Files` to ensure the `BuildConfig` class is updated with the new API key.

### 5. Debug Mode (Mock Repository)
By default, the project uses a mock repository to simulate weather data when running in debug mode. This helps with testing without making real network calls.

**Note**: When using the mock repository, you can only search for "London" to get valid weather data. If you search for any other city, the mock data will not return valid results.

If you'd like to see real weather data and make actual network calls, you need to modify the ViewModel to use the real repository instead of the mock repository.

#### To switch to the real repository:
- Open `HomeViewModel`.
- Update the repository call to use the actual `WeatherRepository` instead of `MockWeatherRepository`.

For example, change:

```kotlin
private val weatherRepository: WeatherRepository = MockWeatherRepository()
```

to

```kotlin
private val weatherRepository: WeatherRepository = WeatherRepository()
```

### 6. Build and Run the App
Once you've configured the API key and selected your desired repository, you can build and run the app in Android Studio by clicking `Run` or using the shortcut `Shift + F10`.

### 7. Testing
You can now test the app by entering the name of a city, and the app will fetch and display the weather data. If you are running the app in debug mode, the mock data will be displayed by default.

**Note**: When using real API calls, ensure that your device or emulator has an active internet connection.
