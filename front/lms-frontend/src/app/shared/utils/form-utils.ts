import {Attribute} from "../../core/entity-utils/attribute";
import {Validators} from "@angular/forms";
import {TreeNode} from "primeng/api";
import {AppUtils} from "./app-utils";
import {SearchDTO} from "../../core/entity-utils/search-dto";
import {SearchFilter} from "../../core/entity-utils/search-filter";
import {SearchOperator} from "../../core/entity-utils/search-operator";
import {TranslationService} from "../../core/services/translation.service";

export class FormUtils {
  static getSearchValidators(attr: Attribute) {
    const validators = [];
    if (attr.searchField?._required) {
      validators.push(Validators.required);
    }
    if (attr.searchField?._maxLength && typeof attr.searchField._maxLength === 'number') {
      validators.push(Validators.maxLength(attr.searchField?._maxLength));
    }
    if (attr.searchField?._minLength && typeof attr.searchField._minLength === 'number') {
      validators.push(Validators.minLength(attr.searchField?._minLength));
    }
    return validators;
  }

  static getInputValidators(attr: Attribute) {
    const validators = [];
    if (attr.inputField?._required) {
      validators.push(Validators.required);
    }
    if (attr.inputField?._maxLength && typeof attr.inputField._maxLength === 'number') {
      validators.push(Validators.maxLength(attr.inputField?._maxLength));
    }
    if (attr.inputField?._minLength && typeof attr.inputField._maxLength === 'number') {
      validators.push(Validators.minLength(attr.inputField?._maxLength));
    }
    return validators;
  }

  public static extractValueFromSearchDTO(name: string, searchDTO: SearchDTO): any {
    // Check the filters in the FilterGroup recursively
    return this.findValueInFilters(name, searchDTO.filterGroup.filters);
  }

  public static convertDataToTreeNodes(data: any[], attr: Attribute, attributes: Attribute[], expanded: boolean = false, colored: boolean = false): TreeNode[] {
    const nodeMap: { [key: number]: TreeNode } = {};
    const treeNodes: TreeNode[] = [];

    // Check if the attribute has potential for a tree structure
    const attrType = AppUtils.getAttributeType(attr.attr_name, attributes);
    const structure = AppUtils.getFkAttributeStructure(attr.attr_name, attributes);

    if (!structure) {
      return treeNodes;
    }

    // We need to find the self referenced attribute to start with tree creation, because only entities that contain a reference to itself can be structured like a tree.
    const selfReferencedAttribute: Attribute | undefined = structure.attributes.find((attr) => attr.type === structure.entityName);

    if (!selfReferencedAttribute) {
      return treeNodes;
    }

    // Proceed with tree structure creation
    data.forEach((value) => {
      nodeMap[value.id] = {
        key: value.id.toString(),
        label: AppUtils.getEntityViewValue(value, attrType, " "),
        data: value,
        expanded: expanded,
        type: attrType,
        children: [],
      };
    });

    data.forEach((value) => {
      const parentId = value[selfReferencedAttribute.attr_name]?.id;
      if (parentId && nodeMap[parentId]) {
        nodeMap[parentId].children?.push(nodeMap[value.id]);
      } else {
        treeNodes.push(nodeMap[value.id]);
      }
    });

    treeNodes.forEach(node => assignColors(node, 0, colored));
    return treeNodes;
  }

  public static filterTreeNodes(treeNodes: TreeNode[], filterFn: (data: any) => boolean): TreeNode[] {
    const filterNodes = (nodes: TreeNode[]): TreeNode[] => {
      return nodes
        .map(node => {
          // Recursively filter children
          const filteredChildren = filterNodes(node.children || []);

          // If node matches filter or has any children that match, include it
          if (filterFn(node.data) || filteredChildren.length > 0) {
            return {
              ...node,
              children: filteredChildren // Keep the filtered children that match
            } as TreeNode;
          }
          return undefined; // Return undefined if no match
        })
        .filter((node): node is TreeNode => node !== undefined); // Filter out undefined nodes
    };

    // Start filtering from the root nodes
    return filterNodes(treeNodes);
  }

  static getEnumOptions(attr_name: string, translationService: TranslationService) {
    const selectedEnum = Object.keys(AppUtils.findEnumByName(AppUtils.capitalizeFirstLetter(attr_name))).filter(key => isNaN(Number(key)));
    if (selectedEnum) {
      return selectedEnum.map(e => {
        return {
          label: translationService.translate(e),
          value: e
        }
      });
    } else {
      console.error(`Enum with name not found.`);
    }
    return [];
  }

  private static findValueInFilters(name: string, filters: SearchFilter[]): any {
    for (let filter of filters) {
      // Check if the key matches the name and return the value
      if (filter.key === name) {
        if (filter.searchOperator === SearchOperator.BETWEEN && Array.isArray(filter.value)) {
          return [new Date(filter.value[0].slice(0, 19)), new Date(filter.value[1].slice(0, 19))];
        }
        return filter.value;
      }

      // If there are nested filters (logicalOperator + filters), search recursively
      if (filter.filters && filter.filters.length > 0) {
        const value = this.findValueInFilters(name, filter.filters);
        if (value !== undefined) {
          return value;
        }
      }
    }
    return undefined; // Return undefined if the key is not found
  }
}

const colorClasses = [
  'text-color font-bold',
  'text-primary-500 font-bold',
  'text-primary-400 font-bold',
  'text-primary-300 font-bold',
  'text-primary-200 font-bold',
  'text-primary-100 font-bold',
];

// Helper function to assign colors recursively
const assignColors = (node: TreeNode, level: number, colored: boolean) => {
  if (colored) {
    node.styleClass = colorClasses[level % colorClasses.length];
  }
  node.children?.forEach(child => assignColors(child, level + 1, colored));
};
