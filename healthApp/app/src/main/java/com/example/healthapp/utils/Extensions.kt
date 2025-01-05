package com.example.healthapp.utils

import androidx.lifecycle.*
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

/**
 * Collect Flow in a lifecycle-aware manner.
 */
fun <T> Flow<T>.collectInLifecycle(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    collect: suspend (T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            this@collectInLifecycle.collectLatest(collect)
        }
    }
}

fun <T> Flow<T>.collectSafely(
    onError: (Throwable) -> Unit,
    onSuccess: (T) -> Boolean // Ensure this returns Boolean
): Flow<T> {
    return this.catch { error ->
        onError(error)
    }.onEach { value ->
        onSuccess(value) // Expect a Boolean return here
    }
}


/**
 * Observe a LiveData only once.
 */
fun <T> LiveData<T>.observeOnce(observer: Observer<T>) {
    observeForever(object : Observer<T> {
        override fun onChanged(value: T) {
            removeObserver(this)
            observer.onChanged(value)
        }
    })
}

/**
 * Throttle LiveData updates to avoid frequent UI changes.
 */
fun <T> LiveData<T>.throttle(interval: Long): LiveData<T> {
    val result = MediatorLiveData<T>()
    var lastEmissionTime = 0L

    result.addSource(this) { value ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastEmissionTime >= interval) {
            lastEmissionTime = currentTime
            result.value = value
        }
    }
    return result
}

/**
 * Debounce a Flow to emit values only after a delay.
 */
@OptIn(FlowPreview::class)
fun <T> Flow<T>.debounceFlow(debounceTimeMillis: Long): Flow<T> {
    return this.debounce(debounceTimeMillis)
}

/**
 * Retry a Flow with delay.
 */
@OptIn(FlowPreview::class)
fun <T> Flow<T>.retryWithDelay(
    retries: Int,
    delayMillis: Long
): Flow<T> {
    return this.retryWhen { cause, attempt ->
        if (attempt < retries) {
            delay(delayMillis)
            true // Explicitly return true for retry
        } else {
            false // Stop retrying after reaching max retries
        }
    }
}


/**
 * Update a MutableStateFlow safely.
 */
fun <T> MutableStateFlow<T>.update(transform: T.() -> T) {
    this.value = this.value.transform()
}


