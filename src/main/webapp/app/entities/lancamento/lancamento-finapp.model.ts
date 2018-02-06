import { BaseEntity } from './../../shared';

export const enum Tipo {
    'RECEITA',
    'DESPESA'
}

export class LancamentoFinapp implements BaseEntity {
    constructor(
        public id?: number,
        public tipo?: Tipo,
        public data?: any,
        public descricao?: string,
        public valor?: number,
        public pagoRecebido?: boolean,
        public lancamentoId?: number,
        public lancamentoPais?: BaseEntity[],
        public contaId?: number,
        public entidadeId?: number,
        public categoriaId?: number,
        public recorrente?: boolean,
        public quantidadeParcelas?: number,
        public parcela?: number
    ) {
        this.pagoRecebido = false;
        this.recorrente = false;
    }
}
