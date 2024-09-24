// /c:/Users/User/Desktop/MusicRoomReservation-project/FE-MusicRoomReservation-Project/src/fetchUtils/fetch.js

export async function fetchData<T>(url: string): Promise<T> {
  const response = await fetch(url);
  if (!response.ok) {
    throw new Error(`Failed to fetch from ${url}: ${response.statusText}`);
  }
  const data = await response.json();
  return data as T;
}

export default fetchData;
