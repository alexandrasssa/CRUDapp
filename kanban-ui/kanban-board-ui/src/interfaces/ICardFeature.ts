import ICard from "./ICard";
import ICategory from "./ICategory";
import IStatus from "./IStatus";

interface ICardFeature {
  id: string,
  category: ICategory,
  title: string,
  description: string,
  status: IStatus,
  tasks: ICard[] | undefined
}

export default ICardFeature;