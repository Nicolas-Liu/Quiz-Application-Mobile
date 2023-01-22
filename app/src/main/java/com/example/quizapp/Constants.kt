package com.example.quizapp

object Constants {
    const val USER_NAME: String = "username"
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"

    fun getQuestions():ArrayList<Question>{
        val questionsList = ArrayList<Question>()

        val que1 = Question(
            1,
            "What country does this flag belong to?",
            R.drawable.canada,
            "Argentina",
            "Canada",
            "USA",
            "China",
            2 )

        questionsList.add(que1)

        val que2 = Question(
            2,
            "Which martial art did Bruce Lee create?",
            R.drawable.brucelee,
            "Shaolin Kung Fu",
            "Jeet Kune Do",
            "Xingyi Quan",
            "Wing Chun Quan",
            2 )

        questionsList.add(que2)

        val que3 = Question(
            3,
            "What was the original name of New York City?",
            R.drawable.nyc,
            "New Paris",
            "New London",
            "New Rome",
            "New Amsterdam",
            4 )

        questionsList.add(que3)

        return questionsList
    }
}