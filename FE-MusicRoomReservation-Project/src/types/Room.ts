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

interface RoomAndTime extends Room {
  time?: string;
}
