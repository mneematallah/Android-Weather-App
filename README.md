# Android Weather App

This is a simple weather forecast application that I developed. The goal of this project was to gain hands-on experience with Android development, Java networking, and JSON parsing.

## 📱 Features

- Retrieves weather data from the [Open-Meteo API](https://open-meteo.com/en/docs)
- Displays the **average daily temperatures** for the next 7 days in Fahrenheit
- Parses JSON responses and calculates daily averages from hourly temperature data
- Clean, minimal user interface built using `TextViews`
- Designed and tested using Android Studio and the Android Emulator

## 🔧 How It Works

1. A network request is made to:
   https://api.open-meteo.com/v1/forecast?latitude=30.28&longitude=-97.76&hourly=temperature_2m&temperature_unit=fahrenheit
   (Coordinates are set to Austin, TX)
2. JSON data is retrieved and parsed to extract hourly temperatures
3. Temperatures are averaged per day
4. Results are displayed in a scrollable list format on the screen


The app supports optional enhancements:

- Display additional metrics like humidity, wind, pressure, visibility, or rain
- Add visualizations like charts and graphs to improve UI/UX
- Integrate a machine learning-based temperature predictor for tomorrow using the Weka framework and a dataset of at least 100 entries

📚 Technologies Used
Java

Android SDK

Android Studio

Open-Meteo API

JSON Parsing

(Optional) Weka for ML


## 🚀 Getting Started

1. Clone the repository:
```bash
git clone https://github.com/mneematallah/Android-Weather-App.git
