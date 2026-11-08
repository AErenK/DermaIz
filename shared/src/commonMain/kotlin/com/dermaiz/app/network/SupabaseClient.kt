package com.dermaiz.app.network

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient


object Supabase {
    private const val SUPABASE_URL = "https://ldxlzcernkpptttvjnnt.supabase.co"
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImxkeGx6Y2VybmtwcHR0dHZqbm50Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3OTQwNTk0MjYsImV4cCI6MjEwOTYzNTQyNn0.MFUAt2USDVEHzIVK2XszCj5PLBsmkMyYTeNVjqtuvtY"

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Auth)
    }
}