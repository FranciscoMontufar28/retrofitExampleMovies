package com.prueba.francisco.retrofitmoviesexample.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

class LifeCycleDisposable(owner:LifecycleOwner): LifecycleObserver{

    init {
        owner.lifecycle.addObserver(this)
    }

    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    fun add(disposable: Disposable){
        compositeDisposable.add(disposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun dispose(){
        compositeDisposable.dispose()
    }
}