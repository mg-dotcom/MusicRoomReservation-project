interface Room {
  roomId: string;
  name: string;
  building: string;
  instruments: string[];
  capacity: {
    min: number;
    max: number;
  };
  features: string[];
  imageUrl?: string;
  roomType: string;
}

interface BookedRoom extends Room {
  time: string;
}

interface Reservation {
  time: string;
  name: string;
  tel: string;
}

interface RoomStore {
  roomReservation: { [roomId: string]: Reservation[] }; // Changed to an array of reservations
}
