package com.example.latihanandroiddicoding

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var inputText: EditText
    private lateinit var checkButton: Button
    private lateinit var resultTextIterative: TextView
    private lateinit var resultTextRecursive: TextView
    private lateinit var timeTextIterative: TextView
    private lateinit var timeTextRecursive: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputText = findViewById(R.id.inputText)
        checkButton = findViewById(R.id.checkButton)
        resultTextIterative = findViewById(R.id.resultTextIterative)
        resultTextRecursive = findViewById(R.id.resultTextRecursive)
        timeTextIterative = findViewById(R.id.timeTextIterative)
        timeTextRecursive = findViewById(R.id.timeTextRecursive)

        checkButton.setOnClickListener {
            val input = inputText.text.toString()
            val cleanStr = preprocessString(input)
            val timeIterative = measureExecutionTime { checkPalindromeIterative(cleanStr) }
            val isIterativePalindrome = checkPalindromeIterative(cleanStr)
            val timeRecursive = measureExecutionTime { checkPalindromeRecursive(cleanStr, 0, cleanStr.length - 1) }
            val isRecursivePalindrome = checkPalindromeRecursive(cleanStr, 0, cleanStr.length - 1)

            val iterativeResult = if (isIterativePalindrome)
                getString(R.string.is_palindrome)
            else
                getString(R.string.not_palindrome)

            val recursiveResult = if (isRecursivePalindrome)
                getString(R.string.is_palindrome)
            else
                getString(R.string.not_palindrome)

            resultTextIterative.text = getString(R.string.iterative_result, iterativeResult)
            resultTextRecursive.text = getString(R.string.recursive_result, recursiveResult)
            timeTextIterative.text = getString(R.string.iterative_time, timeIterative)
            timeTextRecursive.text = getString(R.string.recursive_time, timeRecursive)
        }
    }

    private fun preprocessString(str: String): String {
        return str.lowercase().replace(Regex("[^a-z0-9]"), "")
    }

    private inline fun measureExecutionTime(block: () -> Unit): Long {
        val repetitions = 10
        var totalTime = 0L
        repeat(repetitions) {
            val startTime = System.nanoTime()
            block()
            val endTime = System.nanoTime()
            totalTime += endTime - startTime
        }
        return totalTime / repetitions
    }

    private fun checkPalindromeIterative(cleanStr: String): Boolean {
        var left = 0
        var right = cleanStr.length - 1

        while (left < right) {
            if (cleanStr[left] != cleanStr[right]) {
                return false
            }
            left++
            right--
        }
        return true
    }

    private fun checkPalindromeRecursive(cleanStr: String, start: Int, end: Int): Boolean {
        if (start >= end) return true
        if (cleanStr[start] != cleanStr[end]) return false
        return checkPalindromeRecursive(cleanStr, start + 1, end - 1)
    }
}