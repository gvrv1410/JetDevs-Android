# JetDevs-Android 

## Overview

This repository contains the implementation of a test task for JetDevs-Android. The primary functionality involves a basic login system and navigation to a dashboard upon successful login. The project demonstrates handling user input, local data storage, and UI navigation.

## Features

1. **Login Screen**:
   - Accepts user input for login credentials.
   - Stores the data locally and validates the login process.
   - Displays a "Successful Login" message upon completion.

2. **Dashboard Screen**:
   - Navigates the user to a dashboard screen post-login.

## Observations

- The API endpoint used in this project accepts all data provided without validation.
- There is no fail case or error handling mechanism returned by the backend API.

## Suggestions

To improve the project:
1. Enhance backend validation to ensure data integrity and reject invalid inputs.
2. Implement client-side error handling to manage API responses effectively.

## How to Run the Project

1. Clone the repository:
   ```sh
   git clone https://github.com/your-username/JetDevs-Android.git
   ```
2. Open the project in Android Studio.
3. Build and run the application on an emulator or physical device.

## Future Improvements

- Introduce a robust backend validation mechanism.
- Add unit tests to ensure code reliability and coverage.
- Implement proper error messages and fail cases in the UI to improve user experience.

