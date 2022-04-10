import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { SubCategoryMasterUpdateComponent } from 'app/entities/sub-category-master/sub-category-master-update.component';
import { SubCategoryMasterService } from 'app/entities/sub-category-master/sub-category-master.service';
import { SubCategoryMaster } from 'app/shared/model/sub-category-master.model';

describe('Component Tests', () => {
  describe('SubCategoryMaster Management Update Component', () => {
    let comp: SubCategoryMasterUpdateComponent;
    let fixture: ComponentFixture<SubCategoryMasterUpdateComponent>;
    let service: SubCategoryMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [SubCategoryMasterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SubCategoryMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubCategoryMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubCategoryMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SubCategoryMaster(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SubCategoryMaster();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
