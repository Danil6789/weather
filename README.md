# Weather Tracker

[![Java](https://img.shields.io/badge/Java-21-red.svg)](https://adoptium.net/)
[![Gradle](https://img.shields.io/badge/Gradle-8.8-blue.svg)](https://gradle.org/)
[![Docker](https://img.shields.io/badge/Docker-✔-blue.svg)](https://www.docker.com/)
[![Spring](https://img.shields.io/badge/Spring-6.1-green.svg)](https://spring.io/)

## Description

A web application for viewing the current weather. Users can register and add one or more locations (cities, villages, and other locations) to their collection, after which the application's main page displays a list of locations with their current weather.

## Frontend REST API Interface
Frontend - https://github.com/zhukovsd/weather-viewer-html-layouts

## Technologies

| Component | Technology |
|-----------|------------|
| **Backend** | Java 21, Spring Framework 6.1 (MVC, ORM), Hibernate 6.4 |
| **Database** | PostgreSQL 15 |
| **Connection Pool** | HikariCP |
| **JSON** | Jackson |
| **Mapping** | MapStruct |
| **Database Migration** | Flyway |
| **Frontend** | Thymeleaf, Bootstrap 5 |
| **Containerization** | Docker, Docker Compose |
| **Web Server** | Tomcat 11.0, Nginx |
| **Security** | BCrypt Password Encoder |
| **Build Tool** | Gradle 8.8 |

## Deployment

### For VPS (Docker)

```bash
# Install Docker
curl -fsSL https://get.docker.com -o get-docker.sh
sh get-docker.sh

# Install Docker Compose plugin
apt install docker-compose-plugin -y

# Clone project
git clone https://github.com/your-username/weather-tracker.git
cd weather-tracker

# Create .env file
cat > .env << EOF
DB_USERNAME=postgres
DB_PASSWORD=your_password
OPENWEATHER_API_KEY=your_api_key
EOF

# Run
docker compose up -d
```

## OpenWeatherMap API Integration

Weather Tracker uses OpenWeatherMap API to fetch weather data and geocoding information. All requests require an API key which can be obtained for free at [OpenWeatherMap](https://openweathermap.org/api).

### API Endpoints Used

| Method | URL | Description |
|--------|-----|-------------|
| `GET` | `/geo/1.0/direct` | Geocoding - convert city name to coordinates |
| `GET` | `/data/2.5/weather` | Get current weather by coordinates |

---

## 1. Geocoding API (City Search)

Converts a city name to geographic coordinates (latitude, longitude).

### Request Parameters

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `q` | string | ✅ Yes | City name (e.g., "London", "Paris") |
| `limit` | integer | ❌ No | Number of results (max 5) |
| `appid` | string | ✅ Yes | Your API key |

### Request Example

```http
GET http://api.openweathermap.org/geo/1.0/direct?q=London&limit=5&appid=YOUR_API_KEY
```
### Response Example

```json
[
  {
    "name": "London",
    "country": "GB",
    "lat": 51.5073219,
    "lon": -0.1276474,
    "state": "England"
  },
  {
    "name": "London",
    "country": "CA",
    "lat": 42.9832406,
    "lon": -81.243372,
    "state": "Ontario"
  }
]
```
### Response Fields

| Field | Description |
|-------|-------------|
| `name` | City name |
| `country` | Country code (ISO 3166) |
| `lat` | Latitude |
| `lon` | Longitude |
| `state` | State/province (if applicable) |

### Request Example

```http
GET https://api.openweathermap.org/data/2.5/weather?lat=51.5073&lon=-0.1276&units=metric&appid=YOUR_API_KEY
```

### Response Example

```json
{
  "main": {
    "temp": 15.5,
    "feels_like": 14.2,
    "humidity": 76
  },
  "weather": [
    {
      "id": 801,
      "main": "Clouds",
      "description": "few clouds",
      "icon": "02d"
    }
  ],
  "wind": {
    "speed": 3.6,
    "deg": 240
  },
  "name": "London"
}
```

### Response Fields

| Field | Description |
|-------|-------------|
| `main.temp` | Current temperature (Celsius when `units=metric`) |
| `main.feels_like` | Human perception of weather |
| `main.humidity` | Humidity percentage |
| `weather[0].description` | Weather condition description |
| `weather[0].icon` | Icon code (for weather icon URL) |
| `wind.speed` | Wind speed (m/s when `units=metric`) |
| `name` | City name (from geocoding) |
