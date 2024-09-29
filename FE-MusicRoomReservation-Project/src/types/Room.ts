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
  time?: string;
}
