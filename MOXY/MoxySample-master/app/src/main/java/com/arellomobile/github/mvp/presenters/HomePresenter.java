package com.arellomobile.github.mvp.presenters;

import com.arellomobile.github.mvp.models.Repository;
import com.arellomobile.github.mvp.views.HomeView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

/**
 * Date: 27.01.2016
 * Time: 19:59
 *
 * @author Yuri Shmakov
 */

@InjectViewState //аннотация для привязывания ViewState к Presenter
public class HomePresenter extends MvpPresenter<HomeView> {

	public void onRepositorySelection(int position, Repository repository) {
		getViewState().showDetails(position, repository);
	}
/* В MvpPresenter содержится экземпляр ViewState, который в тоже время должен реализовывать
тот самый тип View, который пришёл в MvpPresenter.
Доступ к этому экземпляру ViewState можно получить из метода public View getViewState().

Во время разработки вы не думаете, что работаете со ViewState, а просто даёте через этот метод
команды для View, как ей измениться. Так же есть методы для привязывания/отвязывания View от Presenter
(public void attachView(View view) и public void detachView(View view)).

Обратите внимание на то, что к одному Presenter может быть привязано несколько View.
Они будут всегда иметь актуальное состояние(за счёт ViewState). А если вы хотите,
чтобы привязывание/отвязывание View проходило не через стандартное поле ViewState,
то можете переопределить эти методы и работать с пришедшей View как хотите.
Например, вы можете захотеть использовать нестандартный ViewState, который не реализует интерфейс View,
если вам нужно.

В классе MvpPresenter так же есть интересный метод protected void onFirstViewAttach().
Очень важно понять, когда этот метод будет вызван и зачем он нужен. Этот метод вызывается тогда,
когда к конкретному экземпляру Presenter первый раз будет привязана любая View. А когда к этому
Presenter будет привязана другая View, к ней уже будет применено состояние из ViewState.
И здесь уже не важно, эта новая View – совсем другая View, или пересозданная в результате смены
конфигурации. Этот метод подходит для того, чтобы, например, загрузить список новостей при первом
открытии экрана списка новостей.

В момент, когда во View пришла команда, вам может потребоваться понять, это новая команда, или это
команда для восстановления состояния? Например, если это свежая команда, то нужно применить команду
с анимацией. А иначе не надо применять анимацию. Можно это сделать через разные StateStrategy,
или через сложные флаги в Bundle savedState. Но правильным решение будет использовать метод
Presenter(или ViewState) public boolean isInRestoreState(View view), который сообщит вам,
в каком состоянии находится конкретная View. Таким образом вы сможете понять, нужна ли вам анимация,
или нет.*/

/*Так как ViewState в большинстве случаев является довольно однообразной прослойкой между View и
Presenter, был написан генератор кода, который сделает за вас всю грязную работу. Применяя аннотацию
@GenerateViewState к вашему интерфейсу View, вы получите сгенерированный класс ViewState. И чтобы вам
не пришлось в Presenter самостоятельно искать и создавать экземпляр этого класса, есть аннотация
 @InjectViewState. Достаточно просто применить её к классу вашего Presenter. Дальше MvpPresenter
 сам всё сделает – он создаст экземпляр этого ViewState, сложит его себе в качестве поля и будет везде
 использовать его. Вам же просто останется работать с методом public View getViewState() из MvpPresenter.*/

/*В том случае, если вы не хотите использовать @GenerateViewState, но ваш ViewState реализует интерфейс
 View, вы можете по прежнему использовать аннотацию @InjectViewState. В таком случае, передайте в
 эту аннотацию, в качестве параметра, класс вашего ViewState.*/

}
