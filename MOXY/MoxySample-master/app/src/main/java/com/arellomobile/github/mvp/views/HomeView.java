package com.arellomobile.github.mvp.views;

import com.arellomobile.github.mvp.models.Repository;
import com.arellomobile.mvp.MvpView;

/**
 * Date: 27.01.2016
 * Time: 20:00
 *
 * @author Yuri Shmakov
 */
public interface HomeView extends MvpView {
	void showDetails(int position, Repository repository);
}
/*Самым простым компонентом MVP является View. Вам нужно завести интерфейс, который наследуется
от интерфейса-маркера MvpView и описать в нём методы, которые будет уметь выполнять View.

В дополнение ко View, библиотека имеет сущность ViewState, которая непосредственно связана
со View. ViewState является наследником MvpViewState<View extends MvpView>. Он управляет одним,
или несколькими, View(все одного типа View). И каждый раз, когда во ViewState приходит команда
из Presenter, ViewState отправляет её всем View, о которых он знает. Также у MvpViewState есть
метод protected abstract void restoreState(View view), который будет вызван когда какая-нибудь
View будет пересоздана, или когда к Presenter ко ViewState будет привязана новая View. Именно после
того, как выполнится этот метод, „новая“ View примет нужное состояние.

Стоит заметить, что MvpViewState хранит в себе список всех привязанных к нему View. И будет хорошо,
если вы не будете забывать отвязывать View, которые уже уничтожены. Но если вы вдруг забудете это
сделать, сильно не переживайте – в MvpViewState хранятся не прямые ссылки на View, а WeakReference,
что всё-таки поможет GC. А в случае, если вы используете такой механизм, как MvpDelegate, то можете
 не беспокоиться об этом – он как привязывает View к Presenter, так и отвязывает их.*/
