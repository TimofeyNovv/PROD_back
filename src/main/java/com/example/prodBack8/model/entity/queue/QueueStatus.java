package com.example.prodBack8.model.entity.queue;

public enum QueueStatus {
    WAITING,    // Ожидает в очереди
    ACTIVE,     // Получил GPU и работает (перешел в активную сессию)
    CANCELLED,  // Покинул очередь самостоятельно
    COMPLETED   // Автоматически завершен (получил GPU)
}
