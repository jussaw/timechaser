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

export const getWelcomeFormattedDate = (date) => {
  const options = {
    weekday: "short",
    month: "long",
    day: "numeric",
    year: "numeric",
  };
  return new Intl.DateTimeFormat("en-US", options).format(date);
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

export const getFirstAndLastDayOfWeek = (weekNumber, year) => {
  let startDate = new Date(year, 0, 1);
  let dayOfWeek = startDate.getDay();
  let firstMondayOffset = dayOfWeek === 0 ? 1 : 8 - dayOfWeek;

  startDate = new Date(year, 0, 1 + firstMondayOffset + (weekNumber - 2) * 7);
  let endDate = new Date(startDate);

  endDate.setDate(startDate.getDate() + 6);

  return {
    startDate,
    endDate,
  };
};

export const getWeekNumberAndYear = (date) => {
  const inputDate = new Date(date);
  inputDate.setDate(inputDate.getDate() + 3 - ((inputDate.getDay() + 6) % 7));

  const firstDayOfYear = new Date(inputDate.getFullYear(), 0, 1);
  const daysSinceFirstDayOfYear = Math.floor(
    (inputDate - firstDayOfYear) / (24 * 60 * 60 * 1000),
  );

  const weekNumber = Math.ceil((daysSinceFirstDayOfYear + 1) / 7);

  return {
    weekNumber,
    year: inputDate.getFullYear(),
  };
};
