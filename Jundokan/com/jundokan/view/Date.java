package jundokan.view;

public abstract class Date {

	public static java.sql.Date getDate(java.util.Date date) {/*
        java.sql.Date date_sql=new java.sql.Date(new java.util.Date().getTime());
        date_sql = new java.sql.Date(date.getTime());*/
        //return date_sql;
        return new java.sql.Date(date.getTime());
	}
	
	public static int getAge(java.util.Date date){
		int age = 0;			
            java.util.Date currentDate = new java.util.Date();
            int yearInsert=date.getYear(), yearCurrent=currentDate.getYear(), 
            		dayInsert=date.getDate(), dayCurrent=currentDate.getDate(), 
            		monthInsert=date.getMonth(), monthCurrent=currentDate.getMonth();
			if(monthInsert<monthCurrent) age=yearCurrent-yearInsert;
			else if(monthInsert == monthCurrent){
					if(dayInsert<=dayCurrent)age=yearCurrent-yearInsert; 
					else age=(yearCurrent-yearInsert)-1;
					}
			else age=(yearCurrent-yearInsert)-1;		
		return age;
	}

}
