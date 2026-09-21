# 📝 ComposePlayground — Modern Android To-Do App

A modern **To-Do application built with Kotlin and Jetpack Compose**, created as a hands-on project to practice modern Android development patterns, state management, and Material 3 UI.

The project focuses on building a clean, reactive UI using **Jetpack Compose**, with application state managed through a **ViewModel + StateFlow** architecture.

## ✨ Features

* ➕ Add new tasks
* ✅ Mark tasks as completed/incomplete
* 🗑️ Delete tasks with confirmation dialog
* 🧹 Clear all completed tasks
* 🔔 Snackbar notifications for user actions
* 📋 Separate incomplete and completed task sections
* 🎨 Material 3 UI
* 🌙 Compose-ready architecture for theming
* 🔄 Reactive UI updates using `StateFlow`
* ⚡ One-time UI events using `SharedFlow`

## 🛠️ Tech Stack

* **Kotlin**
* **Jetpack Compose**
* **Material 3**
* **Android SDK**
* **ViewModel**
* **StateFlow**
* **SharedFlow**
* **Coroutines**
* **Lifecycle Compose**
* **Gradle**

## 🏗️ Architecture

The application follows a simple **MVVM-style architecture** with unidirectional event flow.

```text
                ┌─────────────────┐
                │      User       │
                └────────┬────────┘
                         │
                         ▼
                ┌─────────────────┐
                │  Compose UI     │
                │  ToDoListScreen  │
                └────────┬────────┘
                         │
                    ToDoEvent
                         │
                         ▼
                ┌─────────────────┐
                │   ViewModel     │
                └───────┬─────────┘
                        │
              ┌─────────┴─────────┐
              ▼                   ▼
        ┌─────────────┐     ┌─────────────┐
        │  StateFlow  │     │ SharedFlow  │
        │  UI State   │     │ UI Events   │
        └──────┬──────┘     └──────┬──────┘
               │                   │
               ▼                   ▼
        ┌────────────────────────────────┐
        │          Compose UI            │
        └────────────────────────────────┘
```

### UI State

The current Todo list is exposed using `StateFlow`.

```kotlin
private val _uiState = MutableStateFlow(ToDoUiState())
val uiState = _uiState.asStateFlow()
```

The UI observes the state using:

```kotlin
val uiState by viewModel.uiState.collectAsStateWithLifecycle()
```

### UI Events

One-time events such as Snackbar messages are exposed using `SharedFlow`.

```kotlin
private val _uiEvent = MutableSharedFlow<ToDoUiEvent>()
val uiEvent = _uiEvent.asSharedFlow()
```

This keeps transient UI events separate from persistent screen state.

## 📂 Project Structure

```text
com.example.comeback
│
├── ui
│   ├── theme
│   │
│   └── todo
│       ├── data
│       │   └── ToDo.kt
│       │
│       ├── event
│       │   ├── ToDoEvent.kt
│       │   └── ToDoUiEvent.kt
│       │
│       ├── screens
│       │   ├── ToDoListAScreen.kt
│       │   └── components
│       │       └── ToDoItem.kt
│       │
│       └── viewmodel
│           └── ToDoViewModel.kt
│
└── MainActivity.kt
```

## 🎯 What I Practiced

This project was built to strengthen practical knowledge of modern Android development, including:

### Jetpack Compose

* `@Composable`
* `Column`
* `Row`
* `Box`
* `LazyColumn`
* `Modifier`
* `Card`
* `Text`
* `Button`
* `TextField`
* `Icon`
* `AlertDialog`
* Material 3 components

### State Management

* `remember`
* `rememberSaveable`
* `StateFlow`
* `MutableStateFlow`
* `SharedFlow`
* `MutableSharedFlow`
* `collectAsStateWithLifecycle`
* `LaunchedEffect`

### Kotlin

* Data classes
* Sealed interfaces
* Immutable lists
* `map`
* `filter`
* `any`
* `copy`
* Null safety
* Lambda expressions

## 🔄 Event Handling

User actions are represented using events rather than directly modifying application state from the UI.

For example:

```kotlin
sealed interface ToDoEvent {
    data class AddToDo(val text: String) : ToDoEvent
    data class DeleteToDo(val todo: ToDo) : ToDoEvent
    data class UpdateToDo(
        val todo: ToDo,
        val isChecked: Boolean
    ) : ToDoEvent

    data object ClearCompleteToDo : ToDoEvent
}
```

The ViewModel handles these events and updates the UI state.

## 🚀 Future Improvements

Planned improvements include:

* ✏️ Edit existing tasks
* 💾 Persistent storage using Room
* 🔍 Search and filter tasks
* 📅 Task due dates
* 🏷️ Task categories/priorities
* 🔔 Local task reminders
* 🌙 Dark/light theme preferences
* 🧪 Unit tests
* 💉 Dependency injection with Hilt

## 📱 Screenshots

Screenshots will be added as the application develops.

## ▶️ Getting Started

### Requirements

* Android Studio
* JDK 17+
* Android SDK
* Android device or emulator

### Installation

Clone the repository:

```bash
git clone https://github.com/harikrishnanponath/ComposePlayground.git
```

Open the project in **Android Studio**, allow Gradle to sync, and run the application on an emulator or physical Android device.

## 📚 Purpose

This project is part of my practical learning journey with **Kotlin and modern Android development**.

Rather than following tutorials passively, I am using the project to implement Android concepts through progressively more complete features and architecture.

## 👨‍💻 Author

**Harikrishnan Ponath Gopinadhan**

Android Developer
Kotlin • Jetpack Compose • Android SDK • MVVM

[GitHub](https://github.com/harikrishnanponath) • [LinkedIn](https://www.linkedin.com/)
