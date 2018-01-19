# Kotatu-ktor-klaxon
[![](https://jitpack.io/v/toliner/Kotatu-ktor-klaxon.svg)](https://jitpack.io/#toliner/Kotatu-ktor-klaxon)  
Kotatu-ktor-klaxon is library to use klaxon for JsonParser in Ktor.

# install
```groovy
repositories {		
	maven { url 'https://jitpack.io' }
}

dependencies {
	compile 'com.github.toliner:Kotatu-ktor-klaxon:0.1.2'
}

```

# How to use
## Use default Klaxon Converter
```kotlin
fun Application.main() {
    install(ContentNegotiation) {
        klaxon()
    }
}
```
## Use Custom Converter
```kotlin
fun Application.main() {
    install(ContentNegotiation) {
        klaxon {
            converter(YourCustomConverter())
        }
    }
}
```
