package ru.kpfu.itis.stayintouch.utils

const val EMAIL_REGEX = "^((\\w[^\\W]+)[.\\-]?)+@(([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\" + ".[0-9]{1,3})|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
const val PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&#])[A-Za-z\\d@\$!%*?&#]{8,}\$"

const val NEWS_FRAGMENT_TAG = "NewsFragment"
const val RECOMMENDATION_FRAGMENT_TAG = "RecommendFragment"
const val ANSWERS_FRAGMENT_TAG = "AnswersFragment"
const val PROFILE_FRAGMENT_TAG = "ProfileFragment"
const val ANSWER_COMMENT_DIALOG_TAG = "AnswerCommentDialog"

const val SHARED_PREFERENCES_LOGGED = "logged"
const val USER_ID = "user_id"
const val POST_ID = "post_id"
const val POST = "post"
const val TOKEN = "token"
const val SEARCH = "search"
const val SHARED_PREFS = "Shared_prefs"

const val CODE_400 = 400
const val CODE_500 = 500

const val LOGIN_DATA_ERROR = "Unable to log in with provided credentials"
const val SIGN_UP_EMAIL_EXISTS_ERROR = "A user is already registered with this e-mail address"

const val PROFILE_IMAGE_SIZE_SMALL = 48
const val PROFILE_IMAGE_SIZE_MEDIUM = 96

const val PICK_IMAGE_REQUEST = 1

const val COUNT_OF_ELEMENTS = 20