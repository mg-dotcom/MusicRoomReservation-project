const capitalizeAndSpace = (roomType: string): string => {
  const formatted = roomType.replace(/([A-Z])/g, " $1").trim();
  return formatted.charAt(0).toUpperCase() + formatted.slice(1);
};

export { capitalizeAndSpace };
