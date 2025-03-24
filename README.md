# 04-11-abschluss

# One Piece App – Android Edition

**Entdecke die Welt von One Piece – kompakt, bunt und überall dabei!**

Diese Android-App bringt dir die Charaktere, Crews und das Flair der legendären Serie One Piece direkt aufs Smartphone. Jede Crew hat ihre eigene Farbe, und das dunkle Theme sorgt für stylischen Look und klare Lesbarkeit.

Ideal für Fans, Sammler und Neugierige, die mehr über die Charaktere der Grand Line erfahren möchten!

---

## Design

Die App nutzt **Jetpack Compose** für ein modernes UI mit dunklem Theme. Jede Crew wird farblich hervorgehoben, damit du dich direkt zurechtfindest.

---

## Features

- [x] Anzeige von One Piece Crews mit individuellen Farben
- [x] Dunkles Design für bessere Lesbarkeit
- [x] Anzeige von Charakteren pro Crew
- [ ] Detailansicht mit Biografie und Bildern
- [ ] Favoriten-Funktion (mit Room)
- [ ] Teilen von Charakterkarten als Bild

---

## Technischer Aufbau

### Architektur

Die App verwendet das **MVVM-Pattern** mit folgenden Komponenten:

- `ViewModel`: Zustandsverwaltung und Logik
- `Repository`: API-Handling und lokale Datenquellen
- `Model`: Datenklassen für Crews und Charaktere
- `UI`: Jetpack Compose Komponenten

### Datenspeicherung

- **Room**: Für das Speichern von Favoriten und Offline-Daten (geplant)
- **SharedPreferences**: Für einfache Einstellungen (z. B. Theme)

### API

- Nutzt eine One Piece API (z. B. [https://onepieceapi.dev](https://onepieceapi.dev) – je nach Verfügbarkeit)
- Endpunkte: Crews, Charaktere, Bilder, Biografien

### Verwendete Libraries

- **Jetpack Compose** – Modernes UI-Toolkit
- **Retrofit + Moshi** – API-Handling und JSON Parsing
- **Room** – Lokale Datenbank
- **Coil** – Schnelles Laden und Caching von Bildern
- **Hilt** – Dependency Injection

---

## Ausblick

- [ ] Offline-Modus für gespeicherte Charaktere
- [ ] Quiz-Modus mit One Piece Trivia
- [ ] Sprachumschaltung (Deutsch/Englisch/Japanisch)
- [ ] Animationen mit Lottie oder Compose Animations
- [ ] Notification-Feature bei neuen Charakteren

---
