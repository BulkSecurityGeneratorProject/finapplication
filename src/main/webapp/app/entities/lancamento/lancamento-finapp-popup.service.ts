import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { LancamentoFinapp } from './lancamento-finapp.model';
import { LancamentoFinappService } from './lancamento-finapp.service';

@Injectable()
export class LancamentoFinappPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private lancamentoService: LancamentoFinappService

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
                this.lancamentoService.find(id).subscribe((lancamento) => {
                    if (lancamento.data) {
                        lancamento.data = {
                            year: lancamento.data.getFullYear(),
                            month: lancamento.data.getMonth() + 1,
                            day: lancamento.data.getDate()
                        };
                    }
                    this.ngbModalRef = this.lancamentoModalRef(component, lancamento);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.lancamentoModalRef(component, new LancamentoFinapp());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    lancamentoModalRef(component: Component, lancamento: LancamentoFinapp): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.lancamento = lancamento;
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
