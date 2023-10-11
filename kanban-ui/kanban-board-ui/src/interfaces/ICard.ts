import IStatus from "./IStatus";

interface ICard {
  id: string,
  featureId: string,
  title: string,
  description: string,
  status: IStatus,
}

export default ICard;