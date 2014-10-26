package com.launcher.parthlauncher;

public class SortApps {
public void exchange_sort(MainActivity.Pac pacs[])
{
	int i,j;
	MainActivity.Pac temp;
	for(i=0;i<pacs.length;i++)
	{
		for(j=i+1;j<pacs.length;j++)
		{
			if(pacs[i].label.compareToIgnoreCase(pacs[j].label)>0)
			{
				
				temp=pacs[i];
				pacs[i]=pacs[j];
				pacs[j]=temp;
			}
		}
	}
}
}
