const capitalizeAndSpace = (roomType: string): string => {
  const formatted = roomType.replace(/([A-Z])/g, " $1").trim();
  return formatted.charAt(0).toUpperCase() + formatted.slice(1);
};

const localTimeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;

const currentDate = new Date().toLocaleDateString("en-GB", {
  timeZone: localTimeZone,
  day: "numeric",
  month: "long",
  year: "numeric",
});

const currentDayDate = new Date()
  .toLocaleDateString("en-GB", {
    timeZone: localTimeZone,
    weekday: "long",
    day: "numeric",
    month: "long",
    year: "numeric",
  })
  .replace(" ", ", ");

export { capitalizeAndSpace, currentDate, localTimeZone, currentDayDate };
