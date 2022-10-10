export class GenericListDTO<T> {
    content: T[] = [];
    pageable: any = {};
    totalPages: number;
    totalElements: number;
    size: number;
    numberOfElements: number;
}