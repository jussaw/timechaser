export const getWeek = (sundayDate) => {
  let week = [];

  for (let i = 0; i < 7; i++) {
    week.push(getDateAfterXDays(sundayDate, i));
  }

  return week;
};

export const getFormattedDate = (date) => {
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const year = date.getFullYear();

  const formattedMonth = month.toString().padStart(2, "0");
  const formattedDay = day.toString().padStart(2, "0");

  return `${formattedMonth}/${formattedDay}/${year}`;
};

export const getDateAfterXDays = (date, days) => {
  const resultDate = new Date(date);
  resultDate.setDate(date.getDate() + days);

  return resultDate;
};

export const getSundayOfCurrentWeek = () => {
  const today = new Date();
  const dayOfWeek = today.getDay();
  const diff = today.getDate() - dayOfWeek;
  const sunday = new Date(today.setDate(diff));

  sunday.setHours(0);
  sunday.setMinutes(0);
  sunday.setSeconds(0);
  sunday.setMilliseconds(0);

  return sunday;
};
