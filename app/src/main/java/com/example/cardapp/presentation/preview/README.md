# 📱 UI Preview Functions - CardApp

Этот пакет содержит Preview функции для всех UI компонентов приложения **CardApp**. Эти функции позволяют просматривать и тестировать дизайн интерфейса прямо в Android Studio без запуска приложения.

## 🎨 Как использовать Preview

1. **Откройте любой файл с Preview функциями**
2. **Нажмите на кнопку "Design" или "Split"** в правом верхнем углу
3. **Просматривайте все Preview одновременно** или кликните по конкретному
4. **Тестируйте интерактивность** с помощью Interactive Preview

## 📋 Доступные Preview

### 🔍 **Search Screen** (`BinSearchScreen.kt`)
- `BinInfoCardPreview` - Полная карточка с результатом поиска BIN
- `BinInfoCardLoadingPreview` - Состояние загрузки 
- `BinSearchErrorPreview` - Отображение ошибки

### 📚 **History Screen** (`HistoryScreen.kt`)
- `HistoryItemPreview` - Элемент истории (Visa)
- `HistoryItemMastercardPreview` - Элемент истории (Mastercard)
- `HistoryEmptyStatePreview` - Пустое состояние истории
- `HistoryWithDataPreview` - История со списком элементов
- `HistoryLoadingPreview` - Состояние загрузки истории

### 🖥️ **App Screens** (`AppPreview.kt`)
- `BinSearchScreenPreview` - Полный экран поиска с результатом
- `BinSearchEmptyPreview` - Экран поиска без данных
- `BinSearchPhonePreview` - Адаптивный дизайн для телефонов

### 🌙 **Dark Theme** (`DarkThemePreview.kt`)
- `BinSearchScreenDarkPreview` - Поиск в темной теме
- `HistoryScreenDarkPreview` - История в темной теме
- `BinSearchErrorDarkPreview` - Ошибка в темной теме
- `HistoryEmptyStateDarkPreview` - Пустая история в темной теме
- `BinSearchLoadingDarkPreview` - Загрузка в темной теме

## 🛠 Технические детали

### **Используемые технологии:**
- **Jetpack Compose Preview** - для предварительного просмотра
- **Material Design 3** - компоненты дизайн-системы
- **Sample Data Generation** - тестовые данные для Preview
- **Multi-screen Support** - превью для разных размеров экранов

### **Типы Preview:**
- **Static Preview** - статичное отображение компонентов
- **Interactive Preview** - интерактивное тестирование (в Android Studio)
- **Device Preview** - превью для конкретных устройств
- **Theme Preview** - светлая и темная тема

## 🎯 Лучшие практики

### **Naming Convention:**
- `ComponentNamePreview` - базовое превью
- `ComponentNameDarkPreview` - темная тема
- `ComponentNamePhonePreview` - для телефонов
- `ComponentNameLoadingPreview` - состояние загрузки
- `ComponentNameErrorPreview` - состояние ошибки

### **Sample Data:**
Все Preview используют реалистичные тестовые данные:
- **BIN номера:** 457173, 532130, 424631
- **Банки:** Jyske Bank, Chase Bank, Сбербанк
- **Страны:** Дания, США, Россия

### **Responsive Design:**
- Превью для разных размеров экранов
- Адаптивные компоненты для телефонов и планшетов
- Тестирование в горизонтальной и вертикальной ориентации

## 🚀 Быстрый старт

1. **Откройте файл с Preview функциями**
2. **Включите режим "Design" в Android Studio**
3. **Выберите нужный Preview для просмотра**
4. **Используйте Interactive mode для тестирования**

---

## 👨‍💻 Автор

**Никита Заруба**  
*CardApp - BIN Lookup Application*

---

*Preview функции созданы для быстрой разработки и тестирования UI компонентов в рамках проекта CardApp.* 