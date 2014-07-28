package example.viewmodels;

import com.sage.binding.mvvm.facade.BaseViewModel;

import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;

public class NewMainItemViewModel extends BaseViewModel
{
	public int IsNotShowLoading;
	public void SetIsNotShowLoading(int isNotShowLoading)
	{
		this.IsNotShowLoading = isNotShowLoading;
	}
	
	public int IsShowLoading;
	public void SetIsShowLoading(int isShowLoading)
	{
		this.IsShowLoading = isShowLoading;
	} 
	
	public String TranslationText;
	public void SetTranslationText(String translationText)
	{
		this.TranslationText = translationText;
		NotifyPropertyChanged("TranslationText");
	}
	
	public int TranslationVisibility;
	public void SetTranslationVisibility(int visibility)
	{
		this.TranslationVisibility = visibility;
		NotifyPropertyChanged("TranslationVisibility");
	}
	
	public OnClickListener GoodCommand;

	public void SetGoodCommand(OnClickListener l)
	{
		this.GoodCommand = l;
		NotifyPropertyChanged("GoodCommand");
	}

	public OnClickListener BadCommand;

	public void SetBadCommand(OnClickListener l)
	{
		this.BadCommand = l;
		NotifyPropertyChanged("BadCommand");
	}
	
	public android.text.method.MovementMethod MovementMethod;
	public void SetMovementMethod(android.text.method.MovementMethod m)
	{
		this.MovementMethod = m;
		NotifyPropertyChanged("MovementMethod");
	}

	public NewMainItemViewModel()
	{
		SetIsGoodColor(UncheckedColor);
		SetIsBadColor(UncheckedColor);
		SetGoodCommand(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				SetIsGood(true);
				SetIsBad(false);
			}
		});

		SetBadCommand(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				SetIsBad(true);
				SetIsGood(false);
			}
		});
		SetTranslationVisibility(View.GONE);
		SetIsNotShowLoading(View.VISIBLE);
		SetIsShowLoading(View.GONE);
	}	

	private int CheckedColor = Color.rgb(140, 210, 50);
	private int UncheckedColor = Color.rgb(146, 146, 146);

	public int IsGoodColor;

	public void SetIsGoodColor(int isGoodColor)
	{
		this.IsGoodColor = isGoodColor;
		NotifyPropertyChanged("IsGoodColor");
	}

	public int IsBadColor;

	public void SetIsBadColor(int isBadColor)
	{
		this.IsBadColor = isBadColor;
		NotifyPropertyChanged("IsBadColor");
	}

	public String Content;

	public void SetContent(String content)
	{
		this.Content = content;
		NotifyPropertyChanged("Content");
		SetShowContent(Content);
	}
	
	public CharSequence ShowContent;
	public void SetShowContent(CharSequence showContent)
	{
		this.ShowContent = showContent;
		NotifyPropertyChanged("ShowContent");
	}

	public boolean IsGood = false;

	public void SetIsGood(boolean isGood)
	{
		this.IsGood = isGood;
		NotifyPropertyChanged("IsGood");
		if (isGood)
			SetIsGoodColor(CheckedColor);
		else
			SetIsGoodColor(UncheckedColor);
	}

	public boolean IsBad = false;

	public void SetIsBad(boolean isBad)
	{
		this.IsBad = isBad;
		NotifyPropertyChanged("IsBad");
		if (isBad)
			SetIsBadColor(CheckedColor);
		else
			SetIsBadColor(UncheckedColor);
	}
}
