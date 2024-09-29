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
  roomId: string;
  time: string;
  name: string;
  tel: string;
}

// Define the interface for the room store with an index signature
interface RoomStore {
  roomReservation: { [key: string]: Reservation | undefined }; // Index signature
}
