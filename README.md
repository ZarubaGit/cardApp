# 🃏 CardApp - BIN Lookup Application

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-green.svg" alt="Platform">
  <img src="https://img.shields.io/badge/Language-Kotlin-blue.svg" alt="Language">
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose-orange.svg" alt="UI">
  <img src="https://img.shields.io/badge/Architecture-Clean%20Architecture-purple.svg" alt="Architecture">
</p>

## 📱 Описание приложения

**CardApp** - это современное Android приложение для поиска информации о банковских картах по BIN-номеру (Bank Identification Number). Приложение позволяет получить подробную информацию о банке-эмитенте, стране выпуска, типе карты и других характеристиках по первым 6-8 цифрам номера карты.

### ✨ Основные возможности:

- 🔍 **Поиск по BIN-номеру** - введите первые 6-8 цифр карты
- 🏦 **Информация о банке** - название, сайт, телефон, город
- 🌍 **Данные о стране** - название, валюта, флаг, координаты
- 💳 **Характеристики карты** - тип (debit/credit), платежная система, бренд
- 📱 **Интерактивные элементы** - клик по телефону, сайту, координатам
- 📚 **История поиска** - все запросы сохраняются локально
- 🌙 **Темная тема** - полная поддержка dark mode
- 🚀 **Offline режим** - просмотр кэшированных результатов

## 🛠 Технологии и архитектура

### **Архитектура:**
- **Clean Architecture** - разделение на слои Domain, Data, Presentation
- **MVVM Pattern** - Model-View-ViewModel для презентационного слоя
- **Repository Pattern** - абстракция для работы с данными

### **Технологический стек:**

#### 🎨 **UI & Presentation:**
- **Jetpack Compose** - современный декларативный UI фреймворк
- **Material Design 3** - актуальная дизайн-система Google
- **Navigation Component** - навигация между экранами
- **Compose Preview** - предварительный просмотр UI компонентов

#### 🏗 **Architecture & DI:**
- **Hilt** - Dependency Injection от Google
- **ViewModel** - управление состоянием UI
- **StateFlow** - реактивные потоки данных
- **Coroutines** - асинхронное программирование

#### 🌐 **Networking:**
- **Retrofit 2** - HTTP клиент для REST API
- **OkHttp** - низкоуровневый HTTP клиент
- **Gson** - сериализация/десериализация JSON
- **binlist.net API** - источник данных о BIN-номерах

#### 🗄 **Data & Storage:**
- **Room Database** - локальное хранение SQLite
- **Flow** - реактивные запросы к базе данных
- **SharedPreferences** - настройки приложения

#### 🔧 **Build & Tools:**
- **Kotlin Symbol Processing (KSP)** - генерация кода
- **Version Catalogs** - управление зависимостями
- **Gradle Kotlin DSL** - конфигурация сборки

#### 🧪 **Quality & Testing:**
- **Lint** - статический анализ кода
- **ProGuard/R8** - обфускация и оптимизация
- **Android API Level 35** - поддержка новейших функций

## 📁 Структура проекта

```
app/src/main/java/com/example/cardapp/
├── 📱 presentation/          # UI слой
│   ├── search/              # Экран поиска BIN
│   ├── history/             # Экран истории
│   ├── navigation/          # Навигация между экранами
│   └── preview/             # Preview функции для UI
├── 🏗 domain/               # Бизнес-логика
│   ├── model/               # Модели данных
│   ├── repository/          # Интерфейсы репозиториев
│   └── usecase/             # Use Cases
├── 📊 data/                 # Слой данных
│   ├── remote/              # Сетевые запросы
│   ├── local/               # Локальная база данных
│   └── repository/          # Реализация репозиториев
└── 🔧 di/                   # Dependency Injection
```

## 🚀 Установка и запуск

### Требования:
- **Android Studio** Ladybug | 2024.2.1+
- **JDK 21** (включен в Android Studio)
- **Android SDK API 35**
- **Kotlin 2.0.0**

### Шаги установки:

1. **Клонируйте репозиторий:**
   ```bash
   git clone <repository-url>
   cd cardApp
   ```

2. **Откройте проект в Android Studio**

3. **Синхронизируйте Gradle:**
   ```bash
   ./gradlew sync
   ```

4. **Соберите проект:**
   ```bash
   ./gradlew assembleDebug
   ```

5. **Запустите на устройстве или эмуляторе**

## 🧪 Тестирование

### Тестовые BIN-номера:
- **457173** - Jyske Bank (Дания)
- **532130** - Chase Bank (США)
- **424631** - Сбербанк (Россия)
- **411111** - Visa тестовая карта
- **555555** - MasterCard тестовая карта

### Особенности:
- **Минимум 6 цифр** для валидного BIN
- **HTTP 429** - ограничение API (подождите 2-3 минуты)
- **Кэширование** - повторные запросы работают offline

## 🎯 Ключевые особенности

### **UX/UI:**
- ✅ Современный Material Design 3
- ✅ Поддержка темной темы
- ✅ Адаптивный дизайн для разных экранов
- ✅ Плавные анимации переходов
- ✅ Защита от случайных кликов

### **Производительность:**
- ✅ Lazy loading в списках
- ✅ Кэширование сетевых запросов
- ✅ Оптимизированные UI компоненты
- ✅ R8 минификация для release builds

### **Надежность:**
- ✅ Обработка всех видов ошибок
- ✅ Graceful degradation при отсутствии сети
- ✅ Валидация пользовательского ввода
- ✅ Защита от memory leaks

## 👨‍💻 Автор

**Никита Заруба**

---

*Приложение создано с использованием современных технологий Android разработки и следует лучшим практикам индустрии.* 