import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EntidadeReceitaDespesaFinapp } from './entidade-receita-despesa-finapp.model';
import { EntidadeReceitaDespesaFinappService } from './entidade-receita-despesa-finapp.service';

@Injectable()
export class EntidadeReceitaDespesaFinappPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private entidadeReceitaDespesaService: EntidadeReceitaDespesaFinappService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.entidadeReceitaDespesaService.find(id).subscribe((entidadeReceitaDespesa) => {
                    this.ngbModalRef = this.entidadeReceitaDespesaModalRef(component, entidadeReceitaDespesa);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.entidadeReceitaDespesaModalRef(component, new EntidadeReceitaDespesaFinapp());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    entidadeReceitaDespesaModalRef(component: Component, entidadeReceitaDespesa: EntidadeReceitaDespesaFinapp): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.entidadeReceitaDespesa = entidadeReceitaDespesa;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
