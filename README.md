#📱 Revista UTEQ Móvil

Aplicación móvil Android nativa que consume los web services del sistema de revistas científicas OJS de la Universidad Técnica Estatal de Quevedo (UTEQ), permitiendo navegar por las revistas, sus volúmenes y los artículos publicados.

---

## 🖼️ Capturas del proyecto
<img width="720" height="1600" alt="WhatsApp Image 2026-06-12 at 4 47 03 PM 11" src="https://github.com/user-attachments/assets/08a626e4-aa17-4fb8-a277-fbf6bb827b76" />


---

## 📋 Descripción

La app se conecta a tres web services REST de `revistas.uteq.edu.ec` y presenta la información en tres pantallas encadenadas:

- **Paso 1:** Lista las revistas científicas de la UTEQ.
- **Paso 2:** Al seleccionar una revista, muestra sus volúmenes/ediciones.
- **Paso 3:** Al seleccionar un volumen, muestra los artículos publicados con botones para abrir el **DOI** y el **PDF** directamente en el navegador.

---

## 🛠️ Tecnologías utilizadas

| Tecnología | Detalle |
|---|---|
| **Lenguaje** | Kotlin |
| **IDE** | Android Studio |
| **Diseño de interfaz** | ConstraintLayout |
| **Peticiones HTTP** | Volley 1.2.1 |
| **Carga de imágenes** | Glide 4.16.0 |
| **Parseo JSON** | org.json (nativa de Android) |
| `compileSdk` / `targetSdk` | 34 |
| `minSdk` | 24 (Android 7.0) |

---

## 🌐 Web Services consumidos

| Paso | URL |
|---|---|
| Revistas | `https://revistas.uteq.edu.ec/ws/journals.php` |
| Volúmenes | `https://revistas.uteq.edu.ec/ws/issues.php?j_id={id}` |
| Artículos | `https://revistas.uteq.edu.ec/ws/pubs.php?i_id={id}` |

---

## 📁 Estructura del proyecto

```
app/src/main/java/ec/edu/uteq/ojsuteq/
├── ws/
│   ├── Asynchtask.kt          # Interfaz de callback
│   └── WebService.kt          # Peticiones GET con Volley
├── model/
│   ├── Journals.kt            # Modelo de revista
│   ├── Issues.kt              # Modelo de volumen/edición
│   └── Pubs.kt                # Modelo de artículo
├── adapter/
│   ├── JournalsAdapter.kt
│   ├── IssuesAdapter.kt
│   └── PubsAdapter.kt
├── MainActivity.kt            # Paso 1 — lista revistas
├── IssuesActivity.kt          # Paso 2 — lista volúmenes
└── PubsActivity.kt            # Paso 3 — lista artículos
```

---

## 🚀 Cómo clonar y ejecutar el proyecto

### Requisitos previos

- Android Studio **Hedgehog** o superior
- JDK 17
- Dispositivo físico o emulador con **Android 7.0 (API 24)** o superior
- Conexión a Internet activa

### Pasos

**1. Clonar el repositorio**
```bash
git clone https://github.com/tu-usuario/Revista-UTEQ-Movil.git
```

**2. Abrir en Android Studio**
```
File > Open > seleccionar la carpeta "Revista UTEQ MOVIL"
```

**3. Esperar la sincronización de Gradle**

Android Studio descarga automáticamente las dependencias (Volley y Glide). Esperar a que finalice la barra de progreso inferior.

**4. Ejecutar la app**

Conectar el dispositivo por USB con **depuración USB activada** o seleccionar un emulador, luego presionar **Run** (`Shift + F10`).

> Si hay conflictos de versión de Gradle, usar **File > Invalidate Caches / Restart**.

---

## 📦 Dependencias principales

```kotlin
// build.gradle.kts (módulo app)
implementation("com.android.volley:volley:1.2.1")
implementation("com.github.bumptech.glide:glide:4.16.0")
implementation("androidx.constraintlayout:constraintlayout:2.1.4")
```

---

## 👨‍💻 Autor

**Washington Apunte**  
Ingeniería de Software — 6to Semestre  
Universidad Técnica Estatal de Quevedo (UTEQ)
